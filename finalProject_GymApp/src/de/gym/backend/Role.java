package de.gym.backend;

import java.util.Objects;

public class Role {

	/** Role's unique ID from the database */
	private int role_id;
	/** Role's name */
	private String roleName;

	/**
	 * Role's constructor
	 * 
	 * @param role_id
	 * @param roleName
	 */
	public Role(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the role_id
	 */
	public int getRole_id() {
		return role_id;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param role_id the role_id to set
	 */
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(roleName, role_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Role)) {
			return false;
		}
		Role other = (Role) obj;
		return Objects.equals(roleName, other.roleName) && role_id == other.role_id;
	}

	@Override
	public String toString() {
		return "Role [role_id=" + role_id + ", roleName=" + roleName + "]";
	}

}
