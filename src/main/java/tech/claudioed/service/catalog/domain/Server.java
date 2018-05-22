package tech.claudioed.service.catalog.domain;

import io.vertx.core.Vertx;
import lombok.val;

/**
 * @author claudioed on 21/05/18.
 * Project service-catalog
 */
public class Server {

  public static void main(String[] args){
    final Vertx vertx = Vertx.vertx();
    val repository = new ServiceCatalogRepository(vertx.sharedData());
    vertx.deployVerticle(new APIsVerticle(repository));
    vertx.deployVerticle(new ServiceCatalogRegister(repository));
  }

}
