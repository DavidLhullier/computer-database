package persistence.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import logger.CDBLogger;
import model.Computer;
import model.Page;
import persistence.binding.mapper.ComputerMapper;
import persistence.dto.ComputerDB;

@Repository
@Transactional
public class ComputerDAO {
	
	private final String REQUEST_COUNT_ALL = "select COUNT(cp.id) from ComputerDB cp  ";
	private final String REQUEST_COUNT_COMPUTER_WITH_SEARCH = "select COUNT(cp.id) from ComputerDB cp left join cp.company where cp.name like :search or cp.company.name like :searchBis";
	private final String REQUEST_GET_COMPUTER_PAGE = "from ComputerDB cp left join fetch cp.company ";
	private final String REQUEST_GET_ALL_COMPUTER = "from ComputerDB as cp left join fetch cp.company";
	private final String REQUEST_GET_ONE_COMPUTER_BY_ID = "from ComputerDB cp left join fetch cp.company where cp.id = :id ";
	
	private final String REQUEST_DELETE_ONE_COMPUTER_BY_ID = "delete from ComputerDB where id = :id ";
	private final String REQUEST_DELETE_COMPUTER_BY_COMPANY_ID = "delete from ComputerDB  cp where cp.company = :companyId ;";
	
	private final String REQUEST_EDIT_ONE_COMPUTER_BY_ID = "update ComputerDB set name = :name , introduced = :introduced , discontinued = :discontinued , company_id = :companyId where id = :id ";
	
	private final String REQUEST_GET_ALL_COMPUTER_WITH_RESEARCH_AND_ORDER_BY ="from ComputerDB cp left join fetch cp.company where cp.name like :search or cp.company.name like :searchBis ";

	private ComputerMapper computerMapper;
	private SessionFactory sessionFactory;
	
	@Autowired
	public ComputerDAO(	ComputerMapper computerMapper, SessionFactory session) {
		this.computerMapper = computerMapper;
		this.sessionFactory = session;
	}

	public int countAllComputer() {
		Session session = sessionFactory.openSession();
		Query<?> query = session.createQuery(REQUEST_COUNT_ALL);
		return Integer.valueOf(query.uniqueResult().toString());
	}
	
	public int countAllComputerWithSearch(String research) {		
		Session session = sessionFactory.openSession();
		String request = REQUEST_COUNT_COMPUTER_WITH_SEARCH ;
		if(research == null || research.isEmpty()) {
			research = " ";
		} else {
			research = "%"+research+"%" ;
		}
		Query<?> query = session.createQuery(request)
				.setParameter("search", research)
				.setParameter("searchBis", research);
		
		return Integer.valueOf(query.uniqueResult().toString());
	}
	
	public List<Computer> getAllComputer() {
		Session session = sessionFactory.openSession();
		Query<Computer> query = session.createQuery(REQUEST_GET_ALL_COMPUTER, Computer.class);
		return  query.getResultList();
	}
	
	public List<Computer> getComputerPage(Page page ,String orderBy, String dir) {
		Session session = sessionFactory.openSession();
		String request = REQUEST_GET_COMPUTER_PAGE + "order by " + orderBy + " "+ dir ;
		int max = 0;
		if ((page.getNumeroPage() -1)* page.getNbElementByPage() + page.getNbElementByPage() > countAllComputer()) {
			max = countAllComputer();			
		} else {
			max = (page.getNumeroPage() -1)* page.getNbElementByPage() + page.getNbElementByPage();
		}
		List<ComputerDB> query = session.createQuery(request, ComputerDB.class)
				.setFirstResult((page.getNumeroPage() -1)* page.getNbElementByPage())
				.setMaxResults(max)
				.list();

		//CDBLogger.logInfo(query.getQueryString());
		return (ArrayList<Computer>) query.stream()
				.map(s -> computerMapper.fromComputerDBToComputer(s))
				.collect(Collectors.toList());	
	}

	public List<Computer> getComputerResearch(String research, String orderBy, String dir, Page page) {
		Session session = sessionFactory.openSession();
		String request = REQUEST_GET_ALL_COMPUTER_WITH_RESEARCH_AND_ORDER_BY ;
		if(research == null || research.isEmpty()) {
			research = " ";
		} else {
			research = "%"+research+"%" ;
		}
		
		int max = 0;
		if ((page.getNumeroPage() -1)* page.getNbElementByPage() + page.getNbElementByPage() > countAllComputerWithSearch(research)) {
			max = countAllComputerWithSearch(research);			
		} else {
			max = (page.getNumeroPage() -1)* page.getNbElementByPage() + page.getNbElementByPage();
		}
		
		List<ComputerDB> query = session.createQuery(request, ComputerDB.class)
				.setParameter("search", research)
				.setParameter("searchBis", research)
				.setFirstResult((page.getNumeroPage() -1)* page.getNbElementByPage())
				.setMaxResults(max)
				.list();
		//CDBLogger.logInfo(query.getQueryString());
		return (ArrayList<Computer>) query.stream()
				.map(s -> computerMapper.fromComputerDBToComputer(s))
				.collect(Collectors.toList());	
	}

	public void addComputer(Optional<Computer> computer) {
		try( Session session = sessionFactory.openSession() ) {
			ComputerDB computerDB = computerMapper.fromComputerToComputerDB(computer);
			session.save(computerDB);
			
		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	public Computer getComputerById(int id) {
		Session session = sessionFactory.openSession();
		Query<ComputerDB> query = session.createQuery(REQUEST_GET_ONE_COMPUTER_BY_ID, ComputerDB.class)
				.setParameter("id", id);
		return computerMapper.fromComputerDBToComputer(query.getResultList().get(0));
	}
	
	public void editComputerById(int id, Optional<Computer> computer) {
		ComputerDB computerDB = computerMapper.fromComputerToComputerDB(computer);
		try ( Session session = sessionFactory.openSession() ) {
			session.beginTransaction();
			try {
				session.createQuery(REQUEST_EDIT_ONE_COMPUTER_BY_ID)
						.setParameter("name",computerDB.getName())
						.setParameter("introduced", computerDB.getIntroduced())
						.setParameter("discontinued", computerDB.getDiscontinued())
						.setParameter("companyId", computerDB.getCompany().getId())
						.setParameter("id", id)
						.executeUpdate();
				
			} catch( HibernateException e ) {
	    		e.printStackTrace();
	    	}
		} catch( HibernateException e ) {
    		e.printStackTrace();
		}
	
	/*
		MapSqlParameterSource parameterId = new MapSqlParameterSource().addValue("id", id);
		Computer computerDB = namedParameterJdbcTemplate.query(REQUEST_GET_ONE_COMPUTER_BY_ID, parameterId, computerMapper).get(0);

		Object[] parameters = new Object[5];

		if(computer.get().getName() == null) {
			if(computerDB.getName() != null) {
				parameters[0] = computerDB.getName();
			}
		}
		else {
			parameters[0]= computer.get().getName();
		}

		if(computer.get().getIntroduced() == null) {
			parameters[1] = null;
		}
		else {
			parameters[1] = Date.valueOf(computer.get().getIntroduced());
		}

		if(computer.get().getDiscontinued() == null) {
			parameters[2] = null;
		}
		else {
			parameters[2] = Date.valueOf(computer.get().getDiscontinued());
		}

		if(computer.get().getCompany().getId() == 0) {
			parameters[3] = null;

		}
		else {
			parameters[3] =computer.get().getCompany().getId();
		}
		parameters[4] = id;

		jdbcTemplate.update(REQUEST_EDIT_ONE_COMPUTER_BY_ID, parameters);
*/
	}
	
	public void deleteComputerById(int id) {
		try ( Session session = sessionFactory.openSession() ) {
			session.beginTransaction();
			try  {
				session.createQuery(REQUEST_DELETE_ONE_COMPUTER_BY_ID)
						.setParameter("id", id)
						.executeUpdate();
				
				session.getTransaction().commit();
				
			} catch( HibernateException e ) {
	    		e.printStackTrace();
	    		session.getTransaction().rollback();
	    	}
			
		} catch( HibernateException e ) {
    		e.printStackTrace();
    		
    	}
	}

	public void deleteComputerByCompanyId(int id) {
		try ( Session session = sessionFactory.openSession() ) {
			session.beginTransaction();
			try  {
				session.createQuery(REQUEST_DELETE_COMPUTER_BY_COMPANY_ID)
						.setParameter("id", id)
						.executeUpdate();
				
				session.getTransaction().commit();
				
			} catch( HibernateException e ) {
	    		e.printStackTrace();
	    		session.getTransaction().rollback();
	    	}
			
		} catch( HibernateException e ) {
    		e.printStackTrace();
    		
    	}			
	}

	

}