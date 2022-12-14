package si.fri.prpo.skupina5.api.v1;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.Serial;

@ApplicationPath("v1")
@OpenAPIDefinition(info= @Info(title="Primerjalnik cen API",version="v1",
        contact = @Contact(
                name="Katarina Bresar, Nusa Perme",
                url="https://github.com/katarinafaks/primerjalnik_cen",
                email="kb4422@student.uni-lj.si,np4441@student.uni-lj.si"),
        license = @License(name="dev"),
        description = "API storitev za Primerjavo cen."),
        servers = @Server(url="http://localhost:8080/"))
public class PrimerjalnikCenApplication extends Application {
}
