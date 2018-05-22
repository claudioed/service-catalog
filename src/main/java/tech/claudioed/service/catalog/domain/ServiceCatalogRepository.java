package tech.claudioed.service.catalog.domain;

import io.vertx.core.json.Json;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 * @author claudioed on 21/05/18.
 * Project service-catalog
 */
public class ServiceCatalogRepository {

  private final SharedData data;

  public ServiceCatalogRepository(SharedData data) {
    this.data = data;
  }

  public void add(@NonNull ServiceCatalog serviceCatalog){
    final LocalMap<String, String> services = this.data.getLocalMap("services");
    services.put(serviceCatalog.getName(), Json.encode(serviceCatalog));
  }

  public List<ServiceCatalog> services(){
    final LocalMap<String, String> services = this.data.getLocalMap("services");
    return services.values().stream().map(value -> Json.decodeValue(value,ServiceCatalog.class)).collect(
        Collectors.toList());
  }

}
