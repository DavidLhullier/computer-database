package controller.binding.dto;

import java.time.LocalDate;
import logger.CDBLogger;

public class ValidationDTO {

	private static ValidationDTO instance;

	//Singleton
	public static  ValidationDTO getInstance() {
		if(instance == null) {
			instance = new ValidationDTO();
		}
		return instance;
	}

	private ValidationDTO() {
	}

	public void valide(ComputerAddDTO computerDTO) throws Exception {
		valideName(computerDTO);
		valideDate(computerDTO);
		valideCompanyId(computerDTO);
	}

	private void valideCompanyId(ComputerAddDTO computerDTO) throws Exception {
		String computerCompanyId = computerDTO.getCompanyId();
		if(computerCompanyId == null |  computerCompanyId.isEmpty()) {
			// remonter une erreur
			throw new Exception("companyId == null or empty");
		} else {
			Integer.parseInt(computerCompanyId);
		}

	}

	private void valideDate(ComputerAddDTO computerDTO) throws Exception {
		String computerIntroduced = computerDTO.getIntroduced();
		String computerDiscontinued = computerDTO.getDiscontinued();

		if(computerIntroduced == null |  computerIntroduced.isEmpty()) {
			if(computerDiscontinued == null | computerDiscontinued.isEmpty()) {
				// introduced isn't empty so parse discontinued
			} else {
				throw new Exception("discontinued != null and not empty with not null or empty intoduced date");
			}
			

		} else {

			LocalDate.parse(computerIntroduced);
			
			if(computerDiscontinued == null | computerDiscontinued.isEmpty()) {
				// discontinued empty 
								
			} else {
				
				if(LocalDate.parse(computerDiscontinued).isBefore(LocalDate.parse(computerIntroduced))) {
					throw new Exception("Impossible Date Timeline") ;
				}  else {
					LocalDate.parse(computerDiscontinued);
				}
				
			}
		}
	}

	private void valideName(ComputerAddDTO computerDTO) throws Exception {
		String computerName = computerDTO.getName();
		if( computerName.isEmpty() | computerName == null) {
			throw new Exception("name null or empty") ;
		}
	}

}
