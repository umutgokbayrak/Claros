package org.claros.intouch.contacts.models;

/**
 * @author Umut Gokbayrak
 */
public class Contact {
	private Long id;
	private String username;
	private String firstName;
	private String middleName;
	private String lastName;
	private String title;
	private String sex;
	private String gsmNoPrimary;
	private String gsmNoAlternate;
	private String emailPrimary;
	private String emailAlternate;
	private String webPage;
	private String personalNote;
	private String spouseName;
	private String nickName;
	private String homeAddress;
	private String homeCity;
	private String homeProvince;
	private String homeZip;
	private String homeCountry;
	private String homePhone;
	private String homeFaks;
	private String workCompany;
	private String workJobTitle;
	private String workOffice;
	private String workProfession;
	private String workManagerName;
	private String workAssistantName;
	private String workAddress;
	private String workCity;
	private String workProvince;
	private String workZip;
	private String workCountry;
	private String workPhone;
	private String workFaks;
	private String workDepartment;
	private String birthDay;
	private String birthMonth;
	private String anniversaryDay;
	private String anniversaryMonth;
	
	public Contact() {
		username = "";
		firstName = "";
		middleName = "";
		lastName = "";
		title = "";
		sex = "";
		gsmNoPrimary = "";
		gsmNoAlternate = "";
		emailPrimary = "";
		emailAlternate = "";
		webPage = "";
		personalNote = "";
		spouseName = "";
		nickName = "";
		homeAddress = "";
		homeCity = "";
		homeProvince = "";
		homeZip = "";
		homeCountry = "";
		homePhone = "";
		homeFaks = "";
		workCompany = "";
		workJobTitle = "";
		workOffice = "";
		workProfession = "";
		workManagerName = "";
		workAssistantName = "";
		workAddress = "";
		workCity = "";
		workProvince = "";
		workZip = "";
		workCountry = "";
		workPhone = "";
		workFaks = "";
		workDepartment = "";
		birthDay = "";
		birthMonth = "";
		anniversaryDay = "";
		anniversaryMonth = "";
	}

	public String getEmailAlternate() {
		return emailAlternate;
	}
	public void setEmailAlternate(String emailAlternate) {
		this.emailAlternate = emailAlternate;
	}
	public String getEmailPrimary() {
		return emailPrimary;
	}
	public void setEmailPrimary(String emailPrimary) {
		this.emailPrimary = emailPrimary;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getGsmNoAlternate() {
		return gsmNoAlternate;
	}
	public void setGsmNoAlternate(String gsmNoAlternate) {
		this.gsmNoAlternate = gsmNoAlternate;
	}
	public String getGsmNoPrimary() {
		return gsmNoPrimary;
	}
	public void setGsmNoPrimary(String gsmNoPrimary) {
		this.gsmNoPrimary = gsmNoPrimary;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getHomeCity() {
		return homeCity;
	}
	public void setHomeCity(String homeCity) {
		this.homeCity = homeCity;
	}
	public String getHomeCountry() {
		return homeCountry;
	}
	public void setHomeCountry(String homeCountry) {
		this.homeCountry = homeCountry;
	}
	public String getHomeFaks() {
		return homeFaks;
	}
	public void setHomeFaks(String homeFaks) {
		this.homeFaks = homeFaks;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getHomeProvince() {
		return homeProvince;
	}
	public void setHomeProvince(String homeProvince) {
		this.homeProvince = homeProvince;
	}
	public String getHomeZip() {
		return homeZip;
	}
	public void setHomeZip(String homeZip) {
		this.homeZip = homeZip;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPersonalNote() {
		return personalNote;
	}
	public void setPersonalNote(String personalNote) {
		this.personalNote = personalNote;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSpouseName() {
		return spouseName;
	}
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWebPage() {
		return webPage;
	}
	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}
	public String getWorkAddress() {
		return workAddress;
	}
	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}
	public String getWorkAssistantName() {
		return workAssistantName;
	}
	public void setWorkAssistantName(String workAssistantName) {
		this.workAssistantName = workAssistantName;
	}
	public String getWorkCity() {
		return workCity;
	}
	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}
	public String getWorkCompany() {
		return workCompany;
	}
	public void setWorkCompany(String workCompany) {
		this.workCompany = workCompany;
	}
	public String getWorkCountry() {
		return workCountry;
	}
	public void setWorkCountry(String workCountry) {
		this.workCountry = workCountry;
	}
	public String getWorkDepartment() {
		return workDepartment;
	}
	public void setWorkDepartment(String workDepartment) {
		this.workDepartment = workDepartment;
	}
	public String getWorkFaks() {
		return workFaks;
	}
	public void setWorkFaks(String workFaks) {
		this.workFaks = workFaks;
	}
	public String getWorkJobTitle() {
		return workJobTitle;
	}
	public void setWorkJobTitle(String workJobTitle) {
		this.workJobTitle = workJobTitle;
	}
	public String getWorkManagerName() {
		return workManagerName;
	}
	public void setWorkManagerName(String workManagerName) {
		this.workManagerName = workManagerName;
	}
	public String getWorkOffice() {
		return workOffice;
	}
	public void setWorkOffice(String workOffice) {
		this.workOffice = workOffice;
	}
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getWorkProfession() {
		return workProfession;
	}
	public void setWorkProfession(String workProfession) {
		this.workProfession = workProfession;
	}
	public String getWorkProvince() {
		return workProvince;
	}
	public void setWorkProvince(String workProvince) {
		this.workProvince = workProvince;
	}
	public String getWorkZip() {
		return workZip;
	}
	public void setWorkZip(String workZip) {
		this.workZip = workZip;
	}
	public String getAnniversaryDay() {
		return anniversaryDay;
	}
	public void setAnniversaryDay(String anniversaryDay) {
		this.anniversaryDay = anniversaryDay;
	}
	public String getAnniversaryMonth() {
		return anniversaryMonth;
	}
	public void setAnniversaryMonth(String anniversaryMonth) {
		this.anniversaryMonth = anniversaryMonth;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getBirthMonth() {
		return birthMonth;
	}
	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

}
