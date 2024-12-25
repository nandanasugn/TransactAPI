create table public.banners
(
    id                 uuid                  not null
        primary key,
    created_date       timestamp(6),
    deleted            boolean default false not null,
    last_modified_date timestamp(6),
    banner_image       varchar(255)          not null,
    banner_name        varchar(255)          not null,
    description        varchar(255)          not null
);

alter table public.banners
    owner to postgres;

create table public.ppobs
(
    id                 uuid                  not null
        primary key,
    created_date       timestamp(6),
    deleted            boolean default false not null,
    last_modified_date timestamp(6),
    service_code       varchar(255)          not null
        constraint uki0cm1dpnn8wtv811j3nsy17n7
            unique,
    service_icon       varchar(255)          not null,
    service_name       varchar(255)          not null,
    service_tariff     bigint                not null
);

alter table public.ppobs
    owner to postgres;

create table public.users
(
    id                 uuid                  not null
        primary key,
    created_date       timestamp(6),
    deleted            boolean default false not null,
    last_modified_date timestamp(6),
    email              varchar(100)          not null
        constraint uk6dotkott2kjsp8vw4d0m25fb7
            unique,
    password           varchar(100)          not null,
    role               varchar(255)
        constraint users_role_check
            check ((role)::text = ANY ((ARRAY ['USER'::character varying, 'ADMIN'::character varying])::text[]))
    );

alter table public.users
    owner to postgres;

create table public.balances
(
    id                 uuid                  not null
        primary key,
    created_date       timestamp(6),
    deleted            boolean default false not null,
    last_modified_date timestamp(6),
    balance            bigint  default 0     not null,
    user_id            uuid                  not null
        constraint ukb7b18lewd87q4hule8xghvciy
            unique
        constraint fkle2p0xxmbgtiroj0o5w6k7q57
            references public.users
);

alter table public.balances
    owner to postgres;

create table public.transactions
(
    id                 uuid                  not null
        primary key,
    created_date       timestamp(6),
    deleted            boolean default false not null,
    last_modified_date timestamp(6),
    description        varchar(255)          not null,
    invoice_number     varchar(255)          not null
        constraint ukh0xuuo6cibonbr42nw2rq432m
            unique,
    total_amount       bigint                not null,
    transaction_type   varchar(255)
        constraint transactions_transaction_type_check
            check ((transaction_type)::text = ANY
        ((ARRAY ['TOPUP'::character varying, 'PAYMENT'::character varying])::text[])),
    user_id            uuid                  not null
        constraint fkqwv7rmvc8va8rep7piikrojds
            references public.users
);

alter table public.transactions
    owner to postgres;

create table public.user_details
(
    id                 uuid                  not null
        primary key,
    created_date       timestamp(6),
    deleted            boolean default false not null,
    last_modified_date timestamp(6),
    first_name         varchar(100)          not null,
    last_name          varchar(100)          not null,
    profile_image      varchar(255),
    user_id            uuid                  not null
        constraint ukf4pdcamta635qqbhgcyqvrg7f
            unique
        constraint fkicouhgavvmiiohc28mgk0kuj5
            references public.users
);

alter table public.user_details
    owner to postgres;

