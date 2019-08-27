package com.kode.ts.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserCriteria implements Serializable, Criteria {
	
	private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter login;

    private StringFilter firstName;
    
    private StringFilter secondName;
    
    private StringFilter iniciales;

    private StringFilter lastName;

    private StringFilter email;

    private StringFilter imageUrl;

    private IntegerFilter activated;

    private StringFilter langKey;

    private StringFilter createdBy;

    private StringFilter createdDate;

    private StringFilter lastModifiedBy;

    private StringFilter lastModifiedDate;

    private StringFilter authority;

    public UserCriteria() {
        // Empty constructor needed for Jackson.
    }

    public UserCriteria(UserCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.login = other.login == null ? null : other.login.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy(); 
        this.lastName = other.lastName == null ? null : other.lastName.copy(); 
        this.secondName = other.secondName == null ? null : other.secondName.copy(); 
        this.iniciales = other.iniciales == null ? null : other.iniciales.copy(); 
        this.email = other.email == null ? null : other.email.copy(); 
        this.activated = other.activated == null ? null : other.activated.copy(); 
        this.imageUrl = other.imageUrl == null ? null : other.imageUrl.copy(); 
        this.langKey = other.langKey == null ? null : other.langKey.copy(); 
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy(); 
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy(); 
        this.lastModifiedBy = other.lastModifiedBy == null ? null : other.lastModifiedBy.copy(); 
        this.lastModifiedDate = other.lastModifiedDate == null ? null : other.lastModifiedDate.copy(); 
        this.authority = other.authority == null ? null : other.authority.copy(); 
    }
    
    @Override
    public UserCriteria copy() {
        return new UserCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLogin() {
        return login;
    }

    public void setLogin(StringFilter login) {
        this.login = login;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getLastName() {
        return lastName;
    }

    public void setLastName(StringFilter lastName) {
        this.lastName = lastName;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(StringFilter imageUrl) {
        this.imageUrl = imageUrl;
    }

    public IntegerFilter getActivated() {
        return activated;
    }

    public void setActivated(IntegerFilter activated) {
        this.activated = activated;
    }

    public StringFilter getLangKey() {
        return langKey;
    }

    public void setLangKey(StringFilter langKey) {
        this.langKey = langKey;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public StringFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(StringFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public StringFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(StringFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getAuthority() {
        return authority;
    }

    public void setAuthority(StringFilter authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserCriteria that = (UserCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(login, that.login) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName) &&
            Objects.equals(secondName, that.secondName) &&
            Objects.equals(iniciales, that.iniciales) &&
            Objects.equals(email, that.email) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(langKey, that.langKey) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        login,
        firstName,
        lastName,
        secondName,
        iniciales,
        email,
        imageUrl,
        activated,
        langKey,
        createdBy,
        createdDate,
        lastModifiedBy,
        lastModifiedDate,
        authority
        );
    }

    @Override
    public String toString() {
        return "UsuarioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (login != null ? "login=" + login + ", " : "") +
                (firstName != null ? "firstName=" + firstName + ", " : "") +
                (lastName != null ? "lastName=" + lastName + ", " : "") +
                (secondName != null ? "secondName=" + secondName + ", " : "") +
                (iniciales != null ? "iniciales=" + iniciales + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (imageUrl != null ? "imageUrl=" + imageUrl + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
                (langKey != null ? "langKet=" + langKey + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (authority != null ? "authority=" + authority + ", " : "") +
            "}";
    }
	
	public StringFilter getSecondName() {
		return secondName;
	}

	public void setSecondName(StringFilter secondName) {
		this.secondName = secondName;
	}
	
	public StringFilter getIniciales() {
		return iniciales;
	}

	public void setIniciales(StringFilter iniciales) {
		this.iniciales = iniciales;
	}
}
