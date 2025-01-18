create table if not exists streets(
    street_id int not null GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
    street_name varchar(100) not null
);

create table if not exists addresses(
    address_id int not null GENERATED ALWAYS AS IDENTITY  PRIMARY KEY,
    address_interoperability_key varchar(200),
    address_street_number varchar(200),
    address_suffix varchar(200),
    address_cadastral_parcel varchar(200),
    address_longitude varchar(200),
    address_latitude varchar(200),

    street_id int not null,
    foreign key (street_id) references streets(street_id)
);
