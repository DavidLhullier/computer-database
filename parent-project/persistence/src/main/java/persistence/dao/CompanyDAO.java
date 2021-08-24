package persistence.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import core.model.Company;
import persistence.binding.mapper.CompanyMapper;
import persistence.dto.CompanyDB;

@Repository
public class CompanyDAO {

	private final String REQUEST_GET_ALL_COMPANY = "from CompanyDB";
	private final String REQUEST_GET_ONE_COMPANY_BY_ID = "FROM CompanyDB WHERE id = :id";

	private final String REQUEST_DELETE_ONE_COMPANY_BY_ID = "DELETE FROM CompanyDB WHERE id = :id ";

	
	
	private CompanyMapper companyMapper;
	private SessionFactory sessionFactory;
	
	@Autowired
	public CompanyDAO(CompanyMapper companyMapper, SessionFactory session) {
		this.companyMapper = companyMapper;
		this.sessionFactory = session;
	}

	public List<Company> getAllCompany() {
		Session session = sessionFactory.openSession();
		Query<CompanyDB> query = session.createQuery(REQUEST_GET_ALL_COMPANY, CompanyDB.class);
		return (ArrayList<Company>) query.stream()
				.map(s -> companyMapper.fromCompanyDBToCompany(s))
				.collect(Collectors.toList());	
		}

	public Company getCompanyById(int id) {		
		Session session = sessionFactory.openSession();
		Query<CompanyDB> query = session.createQuery(REQUEST_GET_ONE_COMPANY_BY_ID, CompanyDB.class)
				.setParameter("id", id);
		return companyMapper.fromCompanyDBToCompany(query.getResultList().get(0));
	}

	public void deleteCompanyById(int id) {	
		try ( Session session = sessionFactory.openSession() ) {
			session.beginTransaction();
			try  {
				session.createQuery(REQUEST_DELETE_ONE_COMPANY_BY_ID)
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