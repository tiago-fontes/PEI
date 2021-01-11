use ridecare_db;

create table user
(
    id           int auto_increment
        primary key,
    company_name varchar(255) not null,
    email        varchar(255) not null,
    password     varchar(255) not null,
    constraint UK_ob8kqyqqgmefl0aco34akdtpe
        unique (email)
);

create table car
(
    id              int auto_increment
        primary key,
    brand           varchar(255) null,
    fuel            varchar(255) null,
    image           longtext     null,
    license_plate   varchar(255) not null,
    model           varchar(255) null,
    number_of_doors int          not null,
    number_of_seats int          not null,
    sensor_id       int          not null,
    transmission    varchar(255) null,
    year            int          not null,
    user_id         int          not null,
    constraint FKja1j4mm4rqlv6cnhgp1qqgtuj
        foreign key (user_id) references user (id)
);

create table anomaly
(
    id             int auto_increment
        primary key,
    classification varchar(255) not null,
    viewed         bit          not null,
    car_id         int          not null,
    constraint FKqddqiocd7wf21e2xfiy9k5by7
        foreign key (car_id) references car (id)
);

create table measurement
(
    id           int auto_increment
        primary key,
    altitude     float        not null,
    car_location varchar(255) null,
    date         datetime(6)  not null,
    gas          float        not null,
    humidity     float        not null,
    pm10         float        not null,
    pm25         float        not null,
    pressure     float        not null,
    temperature  float        not null,
    anomaly_id   int          not null,
    constraint FKbl7a1lgckql9mcdi6xinfckxu
        foreign key (anomaly_id) references anomaly (id)
);

create table status_history
(
    id     int auto_increment
        primary key,
    date   datetime(6) not null,
    status int         not null,
    car_id int         not null,
    constraint FKnj85krlds9er9kdkcskr7uan3
        foreign key (car_id) references car (id)
);

