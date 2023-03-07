CREATE TABLE IF NOT EXISTS `permission`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY(`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS  `users`(
    `id`                      bigint(20) NOT NULL AUTO_INCREMENT,
    `email`                   varchar(255) DEFAULT NULL,
    `user_name`               varchar(255) DEFAULT NULL,
    `full_name`               varchar(255) DEFAULT NULL,
    `password`                varchar(255) DEFAULT NULL,
    `account_non_expired`     bit(1)       DEFAULT NULL,
    `account_non_locked`      bit(1)       DEFAULT NULL,
    `credentials_non_expired` bit(1)       DEFAULT NULL,
    `enabled`                 bit(1)       DEFAULT NULL,
    `description`             varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS  `beats`(
    `id`          BIGINT NOT NULL AUTO_INCREMENT,
    `url`         VARCHAR(255),
    `title`       VARCHAR(255),
    `uploaded_at` DATETIME,
    `user_id`     BIGINT,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1;

ALTER TABLE `beats`
ADD CONSTRAINT `FK_beat_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

CREATE TABLE IF NOT EXISTS  `beat_tags`(
    `beat_id` BIGINT NOT NULL,
    `tags`    VARCHAR(255),
    PRIMARY KEY (`beat_id`, `tags`)
);

CREATE TABLE IF NOT EXISTS `user_permission` (
    `id_user` bigint(20) NOT NULL,
    `id_permission` bigint(20) NOT NULL,
    PRIMARY KEY (`id_user`,`id_permission`),
    KEY `fk_user_permission_permission` (`id_permission`),
    CONSTRAINT `fk_user_permission` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`),
    CONSTRAINT `fk_user_permission_permission` FOREIGN KEY (`id_permission`) REFERENCES `permission` (`id`)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS config
(
    `key` VARCHAR(255) NOT NULL,
    value VARCHAR(255),
    PRIMARY KEY (`key`)
);

