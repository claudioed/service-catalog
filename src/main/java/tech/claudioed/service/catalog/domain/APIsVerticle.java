package tech.claudioed.service.catalog.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import java.io.IOException;
import java.util.Set;
import lombok.val;

/**
 * @author claudioed on 21/05/18.
 * Project service-catalog
 */
public class APIsVerticle extends AbstractVerticle {

  private static final Logger LOGGER = LoggerFactory.getLogger(APIsVerticle.class);

  private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

  private final ServiceCatalogRepository repository;

  public APIsVerticle(ServiceCatalogRepository repository) {
    this.repository = repository;
  }

  @Override
  public void start() {
    Router router = Router.router(vertx);


    router.get("/api/services").handler(handler ->{
      handler.response().end(Json.encode(this.repository.services()));
    });

    router.route().handler(BodyHandler.create());

    router.post("/upload").handler(routingContext -> {
          Set<FileUpload> fileUploadSet = routingContext.fileUploads();
          for (FileUpload fileUpload : fileUploadSet) {
            Buffer uploadedFile = vertx.fileSystem().readFileBlocking(fileUpload.uploadedFileName());
            try {
              final ServiceCatalog serviceCatalog = MAPPER.readValue(uploadedFile.toString(), ServiceCatalog.class);
              vertx.eventBus().send("new.service.descriptor",Json.encode(serviceCatalog),callback ->{
                routingContext.response().setStatusCode(201).end("OK");
              });
            } catch (IOException e) {
              LOGGER.error(e);
            }
          }
        });

    router.route().handler(StaticHandler.create());

    router.route().handler(CorsHandler.create("*"));

    val server = vertx.createHttpServer();
    server.requestHandler(router::accept).listen(8080);
  }

}
