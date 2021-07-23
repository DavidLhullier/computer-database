package controller.binding.mapper;

import java.sql.Date;

import controller.binding.dto.ComputerAddDTO;
import controller.binding.dto.ValidationDTO;
import logger.CDBLogger;
import model.Company;
import model.Computer;
import model.Computer.ComputerBuilder;
import ui.CLImain;

public class ComputerBindingMapper {

	private ValidationDTO vDTO = ValidationDTO.getInstance();

	public Computer mapToComputer(ComputerAddDTO computerDTO) {		
		try {
			vDTO.valide(computerDTO);
		} catch (Exception e) {
			CDBLogger.logInfo(ComputerBindingMapper.class.toString(), new Exception("défaut de validation")) ;
			e.printStackTrace();
			return null;
		}
		ComputerBuilder computerBuilder = new ComputerBuilder();
		if( !computerDTO.getName().isEmpty() & computerDTO.getName() != null) {
			computerBuilder.setName(computerDTO.getName());
			
			if( !computerDTO.getIntroduced().isEmpty() & computerDTO.getIntroduced() != null) {
				computerBuilder.setIntroduced(Date.valueOf(computerDTO.getIntroduced()).toLocalDate());
			}

			if( !computerDTO.getDiscontinued().isEmpty() & computerDTO.getDiscontinued() != null) {
				computerBuilder.setDiscontinued(Date.valueOf(computerDTO.getDiscontinued()).toLocalDate());
			}

			if( !computerDTO.getCompanyId().isEmpty() & computerDTO.getCompanyId() != null) {
				computerBuilder.setCompany(new Company(Integer.valueOf(computerDTO.getCompanyId())));
			}

			Computer ComputerTmp = computerBuilder.build();
			return ComputerTmp;
		} else {
			return null;
		}

		

	}

}
