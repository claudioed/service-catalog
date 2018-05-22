package tech.claudioed.service.catalog.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * @author claudioed on 21/05/18.
 * Project service-catalog
 */
@Data
public class ServiceCatalog {

  private String name;

  private String description;

  @JsonProperty("contact_emails")
  private List<String> contactEmails;

  private String language;

  private String owner;

  private String status;

  @JsonProperty("alert_service")
  private String alertService;

  @JsonProperty("telemetry_dashboard")
  private String telemetryDashboard;

  private List<String> dependencies;

}
