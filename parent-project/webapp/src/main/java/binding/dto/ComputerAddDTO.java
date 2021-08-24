package binding.dto;

public class ComputerAddDTO {
	
	private String name;
	private String introduced;
	private String discontinued;
	private String companyId;
	
	public ComputerAddDTO(ComputerDTOBuilder computerDTOBuilder) {
		this.name = computerDTOBuilder.name;
		this.introduced = computerDTOBuilder.introduced;
		this.discontinued = computerDTOBuilder.discontinued;
		this.companyId = computerDTOBuilder.companyId;
	}
	
	public ComputerAddDTO() {
		
	}
	
	public String getName() {
		return name;
	}
	public String getIntroduced() {
		return introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	@Override
	public String toString() {
		return ", name=" + name + ", introduced=" + introduced
				+ ", discontinued=" + discontinued + ", companyId=" + this.companyId + "]";
	}
	
	public static class ComputerDTOBuilder {

		private String name;
		private String introduced;
		private String discontinued;
		private String companyId;
		
		public ComputerDTOBuilder setCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}

		public ComputerDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ComputerDTOBuilder setIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerDTOBuilder setDiscontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public ComputerAddDTO build() {
			return new ComputerAddDTO(this);
		}
		
		
	}

	
}
