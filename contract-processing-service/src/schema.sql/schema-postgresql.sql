create table if not exists contract (
    id uuid
        constraint contract_pk primary key ,
    date_start date not null,
    date_end date not null,
    date_send timestamp not null,
    date_create timestamp not null,
    contract_number varchar(250) not null,
    contract_name varchar(250) not null,
    client_api varchar(20) not null,
    contractual_parties jsonb not null
);

create unique index contract_contract_number_uindex
    on contract (contract_number);