package vo;

public class Customer {
	private int customerId;
	private String customerName;
	private String customerAddress;
	private String customerZipcode;
	private String customerPhone;
	private String customerCity;
	private String customerCountry;
	private String customerNotes;
	private int customerSID;
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerAddress="
				+ customerAddress + ", customerZipcode=" + customerZipcode + ", customerPhone=" + customerPhone
				+ ", customerCity=" + customerCity + ", customerCountry=" + customerCountry + ", customerNotes="
				+ customerNotes + ", customerSID=" + customerSID + "]";
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getCustomerZipcode() {
		return customerZipcode;
	}
	public void setCustomerZipcode(String customerZipcode) {
		this.customerZipcode = customerZipcode;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerCity() {
		return customerCity;
	}
	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}
	public String getCustomerCountry() {
		return customerCountry;
	}
	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}
	public String getCustomerNotes() {
		return customerNotes;
	}
	public void setCustomerNotes(String customerNotes) {
		this.customerNotes = customerNotes;
	}
	public int getCustomerSID() {
		return customerSID;
	}
	public void setCustomerSID(int customerSID) {
		this.customerSID = customerSID;
	}

}
