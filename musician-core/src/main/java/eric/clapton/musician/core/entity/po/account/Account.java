package eric.clapton.musician.core.entity.po.account;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import eric.clapton.infrastructure.entity.po.BaseEntity;

@Entity
@Table(name = "ms_account")
@EntityListeners({ AccountListener.class })
public class Account extends BaseEntity {
	private static final long serialVersionUID = -8173121387383620490L;

	private String userName;
	private String phoneNumber;
	private String nickName;
	private String nickNamePinyin;
	private String nickNamePinyin2;
	private AccountType type;
	private String avatar;
	private Gender gender;
	private AccountState state;
	private Calendar lastLoggedIn;
	private Calendar created;
	private String country;
	private String province;
	private String city;
	private String district;
	private String intro;
	private int following;
	private int followers;

	private List<AddressBookEntry> addressBookEntries;
	private List<AccountSkill> skills;

	@Override
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return super.getId();
	}

	@Column(name = "user_name", updatable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "phone_number", updatable = false)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "type")
	@Enumerated
	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}

	@Column(name = "state")
	@Enumerated
	public AccountState getState() {
		return state;
	}

	public void setState(AccountState state) {
		this.state = state;
	}

	@Column(name = "last_logged_in")
	public Calendar getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(Calendar lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	@Column(name = "created", updatable = false)
	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}

	@Column(name = "nick_name")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "nick_name_pinyin")
	public String getNickNamePinyin() {
		return nickNamePinyin;
	}

	public void setNickNamePinyin(String nickNamePinyin) {
		this.nickNamePinyin = nickNamePinyin;
	}

	@Column(name = "nick_name_pinyin_2")
	public String getNickNamePinyin2() {
		return nickNamePinyin2;
	}

	public void setNickNamePinyin2(String nickNamePinyin2) {
		this.nickNamePinyin2 = nickNamePinyin2;
	}

	@Column(name = "country")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "province")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "city")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "district")
	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Column(name = "intro")
	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Column(name = "following")
	public int getFollowing() {
		return following;
	}

	public void setFollowing(int following) {
		this.following = following;
	}

	@Column(name = "followers")
	public int getFollowers() {
		return followers;
	}

	public void setFollowers(int followers) {
		this.followers = followers;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Column(name = "avatar")
	public String getAvatar() {
		return avatar;
	}

	@Column(name = "gender")
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@OrderBy("displayOrder, created desc")
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "owner")
	public List<AddressBookEntry> getAddressBookEntries() {
		return addressBookEntries;
	}

	public void setAddressBookEntries(List<AddressBookEntry> addressBookEntries) {
		this.addressBookEntries = addressBookEntries;
	}

	@OrderBy("skill")
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH }, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "account")
	public List<AccountSkill> getSkills() {
		return skills;
	}

	public void setSkills(List<AccountSkill> skills) {
		this.skills = skills;
	}
}
