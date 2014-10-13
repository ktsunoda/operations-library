package io.berkeley.operations.manifest;


import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Manifest;


@SuppressWarnings("UnusedDeclaration")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ManifestService {

    //------------------------------------------------------------------------------------------------
    // Variables - Private - Static
    //------------------------------------------------------------------------------------------------

    private static final Logger LOGGER = LoggerFactory.getLogger(ManifestService.class);
    private static Manifest manifest;


    //------------------------------------------------------------------------------------------------
    // Variables - Private
    //------------------------------------------------------------------------------------------------

    @Context
    private ServletContext servletContext = null;


    //------------------------------------------------------------------------------------------------
    // Methods - Public
    //------------------------------------------------------------------------------------------------

    @GET
    @Path("/attributes")
    public String getManifestAttribute(@QueryParam("name") String name) {
        return tryGettingManifestAttribute(name);
    }


    //------------------------------------------------------------------------------------------------
    // Methods - Private
    //------------------------------------------------------------------------------------------------

    private Manifest getManifestInstance() {
        // This actually may result in multiple manifest objects getting created
        // if many threads try to get manifest data. However, that's okay.
        if (manifest != null) {
            return manifest;
        }

        InputStream manifestInputStream = null;
        try {
            manifestInputStream = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");
            manifest = new Manifest(manifestInputStream);
        } catch (IOException e) {
            LOGGER.debug(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(manifestInputStream);
        }

        return manifest;
    }


    private String tryGettingManifestAttribute(String name) {
        Manifest manifest = getManifestInstance();
        if (manifest != null) {
            return manifest.getMainAttributes().getValue(name);
        }

        return "Failed to load the manifest.";
    }
}
