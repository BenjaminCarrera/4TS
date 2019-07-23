package com.kode.ts.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String EJECUTIVO = "ROLE_EJECUTIVO";

    public static final String GESTOR_RH = "ROLE_GESTOR_RH";

    public static final String RECLUTADOR = "ROLE_RECLUTADOR";

    public static final String SUPERVISOR_CM = "ROLE_SUPERVISOR_CM";

    public static final String SUPERVISOR_RH = "ROLE_SUPERVISOR_RH";

    private AuthoritiesConstants() {
    }
}
