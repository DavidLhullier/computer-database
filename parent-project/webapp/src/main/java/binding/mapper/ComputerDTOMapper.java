package binding.mapper;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import binding.dto.ComputerAddDTO;
import binding.dto.ValidationDTO;
import logger.CDBLogger;
import core.model.Company;
import core.model.Computer;
import core.model.Computer.ComputerBuilder;

@Component("computerDTOMapper")
public class ComputerDTOMapper {

	@Autowired
	@Qualifier("validationDTO")
	private ValidationDTO vDTO;

	public Optional <Computer> mapToComputer(ComputerAddDTO computerDTO) {		
		try {
			vDTO.valide(computerDTO);
		} catch (Exception e) {
			CDBLogger.logInfo(ComputerDTOMapper.class.toString(), e);
			return Optional.empty();
		}

		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		computerBuilder.setName(computerDTO.getName()).
				withCompany(new Company(Integer.valueOf(computerDTO.getCompanyId()), ""));
		 

		
		if(computerDTO.getIntroduced() != null && !computerDTO.getIntroduced().equals("")) {
			computerBuilder.withIntroduced(Date.valueOf(computerDTO.getIntroduced()).toLocalDate());
		}

		if(computerDTO.getDiscontinued() != null && !computerDTO.getDiscontinued().equals("")) {
			computerBuilder.withDiscontinued(Date.valueOf(computerDTO.getDiscontinued()).toLocalDate());
		}

		Optional<Computer> computerTmp = Optional.ofNullable(computerBuilder.build());
		return computerTmp;



	}

}
