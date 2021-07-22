
CREATE DATABASE If Not Exists  `entdiy_system` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL ON entdiy_system.* TO 'entdiy'@'%';

CREATE DATABASE If Not Exists  `entdiy_gen` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL ON entdiy_gen.* TO 'entdiy'@'%';

CREATE DATABASE If Not Exists  `entdiy_job` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL ON entdiy_job.* TO 'entdiy'@'%';

-- Used by Flyway migration
GRANT SELECT ON performance_schema.* TO 'entdiy'@'%';

flush privileges;
