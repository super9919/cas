package org.apereo.cas.adaptors.x509.authentication.principal;

import org.apereo.cas.adaptors.x509.authentication.CasX509Certificate;
import org.apereo.cas.authentication.CoreAuthenticationTestUtils;
import org.apereo.cas.authentication.credential.UsernamePasswordCredential;
import org.apereo.cas.authentication.handler.support.SimpleTestUsernamePasswordAuthenticationHandler;

import lombok.val;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Scott Battaglia
 * @author Jan Van der Velpen
 * @since 3.0.0.6
 */
@Tag("X509")
public class X509SerialNumberPrincipalResolverTests {
    private static final CasX509Certificate VALID_CERTIFICATE = new CasX509Certificate(true);

    private final X509SerialNumberPrincipalResolver resolver = new X509SerialNumberPrincipalResolver();

    @Test
    public void verifyResolvePrincipalInternal() {
        val c = new X509CertificateCredential(new X509Certificate[]{VALID_CERTIFICATE});
        c.setCertificate(VALID_CERTIFICATE);

        assertEquals(VALID_CERTIFICATE.getSerialNumber().toString(),
            this.resolver.resolve(c, Optional.of(CoreAuthenticationTestUtils.getPrincipal()),
                Optional.of(new SimpleTestUsernamePasswordAuthenticationHandler())).getId());
    }

    @Test
    public void verifySupport() {
        val c = new X509CertificateCredential(new X509Certificate[]{VALID_CERTIFICATE});
        assertTrue(this.resolver.supports(c));
    }

    @Test
    public void verifySupportFalse() {
        assertFalse(this.resolver.supports(new UsernamePasswordCredential()));
    }

    @Test
    public void verifyHexPrincipalOdd() {
        val r = new X509SerialNumberPrincipalResolver(16, true);
        val mockCert = mock(X509Certificate.class);
        when(mockCert.getSerialNumber()).thenReturn(BigInteger.valueOf(300L));

        val principal = r.resolvePrincipalInternal(mockCert);
        assertEquals("012c", principal);
    }

    @Test
    public void verifyHexPrincipalOddFalse() {
        val r = new X509SerialNumberPrincipalResolver(16, false);
        val mockCert = mock(X509Certificate.class);
        when(mockCert.getSerialNumber()).thenReturn(BigInteger.valueOf(300L));

        val principal = r.resolvePrincipalInternal(mockCert);
        assertEquals("12c", principal);
    }

    @Test
    public void verifyHexPrincipalEven() {
        val r = new X509SerialNumberPrincipalResolver(16, true);
        val mockCert = mock(X509Certificate.class);
        when(mockCert.getSerialNumber()).thenReturn(BigInteger.valueOf(60300L));

        val principal = r.resolvePrincipalInternal(mockCert);
        assertEquals("eb8c", principal);
    }
}
