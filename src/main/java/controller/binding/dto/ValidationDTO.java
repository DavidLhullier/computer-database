package controller.binding.dto;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import logger.CDBLogger;
import ui.CLImain;

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

	private void valideCompanyId(ComputerAddDTO computerDTO) {
		String computerCompanyId = computerDTO.getCompanyId();
		if( !computerCompanyId.isEmpty() & computerCompanyId != null) {
			try {
				Integer.parseInt(computerCompanyId);
			} catch (Exception e) {
				
			}

		}

	}

	private void valideDate(ComputerAddDTO computerDTO) throws Exception {
		String computerIntroduced = computerDTO.getIntroduced();
		String computerDiscontinued = computerDTO.getDiscontinued();

		if( !computerIntroduced.isEmpty() & computerIntroduced != null) {

			try {

				LocalDate.parse(computerIntroduced);


			} catch (DateTimeParseException e) {

			}

			if( !computerDiscontinued.isEmpty() & computerDiscontinued != null) {

				try {

					if(LocalDate.parse(computerDiscontinued).isBefore(LocalDate.parse(computerIntroduced))) {
						CDBLogger.logInfo(ValidationDTO.class.toString(), new Exception("Impossible Date Timeline")) ;
					}
				} catch (DateTimeParseException e) {

				}



			}
		} 
		else {
			if( !computerDiscontinued.isEmpty() & computerDiscontinued != null) {

				try {
					LocalDate.parse(computerDiscontinued);

				} catch (Exception e) {

				}

			}
		}

	}

	private void valideName(ComputerAddDTO computerDTO) throws Exception {
		String computerName = computerDTO.getName();
		if( computerName.isEmpty() | computerName == null) {
			CDBLogger.logInfo(ValidationDTO.class.toString(), new Exception("name null or empty")) ;
			throw new Exception();
		}
	}

}
