{...}: {
  project.name = "delivery-company-ana";

  services = {
    postgres = {
      service.image = "postgres:16";
      service.restart = "unless-stopped";

      service.environment = {
        POSTGRES_USER = "admin";
        POSTGRES_PASSWORD = "admin";
        POSTGRES_DB = "courier_service_db";
      };

      service.ports = [
        "5433:5432"
      ];

      service.volumes = [
        "./pgdata:/var/lib/postgresql/data"
      ];
    };
  };
}
