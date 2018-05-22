package tech.claudioed.service.catalog.domain;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.Json;
import lombok.val;

/**
 * @author claudioed on 21/05/18.
 * Project service-catalog
 */
public class ServiceCatalogRegister extends AbstractVerticle {

  private final ServiceCatalogRepository repository;

  public ServiceCatalogRegister(ServiceCatalogRepository repository) {
    this.repository = repository;
  }

  @Override
  public void start() {
    this.vertx.eventBus().<String>consumer("new.service.descriptor",handler ->{
      val data = Json.decodeValue(handler.body(),ServiceCatalog.class);
      this.repository.add(data);
      handler.reply(handler.body());
    });

  }

}
