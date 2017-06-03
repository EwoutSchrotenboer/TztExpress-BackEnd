--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.3
-- Dumped by pg_dump version 9.6.3

-- Started on 2017-06-03 14:30:33

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2222 (class 1262 OID 16414)
-- Name: tztexpress; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "tztexpress" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Dutch_Netherlands.1252' LC_CTYPE = 'Dutch_Netherlands.1252';


ALTER DATABASE "tztexpress" OWNER TO "postgres";

\connect "tztexpress"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2223 (class 0 OID 0)
-- Dependencies: 3
-- Name: SCHEMA "public"; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA "public" IS 'standard public schema';


--
-- TOC entry 1 (class 3079 OID 12387)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS "plpgsql" WITH SCHEMA "pg_catalog";


--
-- TOC entry 2224 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION "plpgsql"; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION "plpgsql" IS 'PL/pgSQL procedural language';


SET search_path = "public", pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 188 (class 1259 OID 16436)
-- Name: address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "address" (
    "id" bigint NOT NULL,
    "address1" character varying(255) NOT NULL,
    "address2" character varying(255),
    "zipcode" character varying(10) NOT NULL,
    "city" character varying NOT NULL
);


ALTER TABLE "address" OWNER TO "postgres";

--
-- TOC entry 187 (class 1259 OID 16434)
-- Name: address_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "address_id_seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "address_id_seq" OWNER TO "postgres";

--
-- TOC entry 2225 (class 0 OID 0)
-- Dependencies: 187
-- Name: address_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "address_id_seq" OWNED BY "address"."id";


--
-- TOC entry 196 (class 1259 OID 16530)
-- Name: externalcourier; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "externalcourier" (
    "id" bigint NOT NULL,
    "companyname" character varying(255) NOT NULL
);


ALTER TABLE "externalcourier" OWNER TO "postgres";

--
-- TOC entry 195 (class 1259 OID 16528)
-- Name: externalcourier_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "externalcourier_id_seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "externalcourier_id_seq" OWNER TO "postgres";

--
-- TOC entry 2226 (class 0 OID 0)
-- Dependencies: 195
-- Name: externalcourier_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "externalcourier_id_seq" OWNED BY "externalcourier"."id";


--
-- TOC entry 198 (class 1259 OID 16538)
-- Name: package; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "package" (
    "id" bigint NOT NULL,
    "originaddressid" bigint NOT NULL,
    "destinationaddressid" bigint NOT NULL,
    "weight" bigint NOT NULL,
    "value" character varying(255) NOT NULL,
    "details" character varying(255)
);


ALTER TABLE "package" OWNER TO "postgres";

--
-- TOC entry 197 (class 1259 OID 16536)
-- Name: package_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "package_id_seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "package_id_seq" OWNER TO "postgres";

--
-- TOC entry 2227 (class 0 OID 0)
-- Dependencies: 197
-- Name: package_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "package_id_seq" OWNED BY "package"."id";


--
-- TOC entry 192 (class 1259 OID 16501)
-- Name: route; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "route" (
    "id" bigint NOT NULL,
    "origin" character varying(255) NOT NULL,
    "destination" character varying(255) NOT NULL
);


ALTER TABLE "route" OWNER TO "postgres";

--
-- TOC entry 191 (class 1259 OID 16499)
-- Name: route_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "route_id_seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "route_id_seq" OWNER TO "postgres";

--
-- TOC entry 2228 (class 0 OID 0)
-- Dependencies: 191
-- Name: route_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "route_id_seq" OWNED BY "route"."id";


--
-- TOC entry 200 (class 1259 OID 16559)
-- Name: shipment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "shipment" (
    "id" bigint NOT NULL,
    "originaddressid" bigint NOT NULL,
    "destinationaddressid" bigint NOT NULL,
    "cost" character varying(255) NOT NULL,
    "packageid" bigint NOT NULL,
    "couriertype" character varying(255) NOT NULL,
    "traincourierid" bigint,
    "externalcourierid" bigint
);


ALTER TABLE "shipment" OWNER TO "postgres";

--
-- TOC entry 199 (class 1259 OID 16557)
-- Name: shipment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "shipment_id_seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "shipment_id_seq" OWNER TO "postgres";

--
-- TOC entry 2229 (class 0 OID 0)
-- Dependencies: 199
-- Name: shipment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "shipment_id_seq" OWNED BY "shipment"."id";


--
-- TOC entry 190 (class 1259 OID 16488)
-- Name: traincourier; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "traincourier" (
    "id" bigint NOT NULL,
    "vogapproved" boolean NOT NULL,
    "identification" character varying(255) NOT NULL,
    "userid" bigint NOT NULL
);


ALTER TABLE "traincourier" OWNER TO "postgres";

--
-- TOC entry 189 (class 1259 OID 16486)
-- Name: traincourier_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "traincourier_id_seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "traincourier_id_seq" OWNER TO "postgres";

--
-- TOC entry 2230 (class 0 OID 0)
-- Dependencies: 189
-- Name: traincourier_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "traincourier_id_seq" OWNED BY "traincourier"."id";


--
-- TOC entry 194 (class 1259 OID 16512)
-- Name: traincourierroute; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "traincourierroute" (
    "id" bigint NOT NULL,
    "day" character varying(50) NOT NULL,
    "traincourierid" bigint NOT NULL,
    "routeid" bigint NOT NULL
);


ALTER TABLE "traincourierroute" OWNER TO "postgres";

--
-- TOC entry 193 (class 1259 OID 16510)
-- Name: traincourierroute_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "traincourierroute_id_seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "traincourierroute_id_seq" OWNER TO "postgres";

--
-- TOC entry 2231 (class 0 OID 0)
-- Dependencies: 193
-- Name: traincourierroute_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "traincourierroute_id_seq" OWNED BY "traincourierroute"."id";


--
-- TOC entry 186 (class 1259 OID 16425)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "user" (
    "id" bigint NOT NULL,
    "firstname" character varying(50) NOT NULL,
    "lastname" character varying(50) NOT NULL,
    "prefix" character varying(30),
    "password" character varying(255) NOT NULL,
    "email" character varying(255) NOT NULL,
    "isemployee" boolean NOT NULL,
    "addressid" bigint NOT NULL
);


ALTER TABLE "user" OWNER TO "postgres";

--
-- TOC entry 185 (class 1259 OID 16423)
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "user_id_seq"
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER TABLE "user_id_seq" OWNER TO "postgres";

--
-- TOC entry 2232 (class 0 OID 0)
-- Dependencies: 185
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "user_id_seq" OWNED BY "user"."id";


--
-- TOC entry 2049 (class 2604 OID 16439)
-- Name: address id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "address" ALTER COLUMN "id" SET DEFAULT "nextval"('"address_id_seq"'::"regclass");


--
-- TOC entry 2053 (class 2604 OID 16533)
-- Name: externalcourier id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "externalcourier" ALTER COLUMN "id" SET DEFAULT "nextval"('"externalcourier_id_seq"'::"regclass");


--
-- TOC entry 2054 (class 2604 OID 16541)
-- Name: package id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "package" ALTER COLUMN "id" SET DEFAULT "nextval"('"package_id_seq"'::"regclass");


--
-- TOC entry 2051 (class 2604 OID 16504)
-- Name: route id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "route" ALTER COLUMN "id" SET DEFAULT "nextval"('"route_id_seq"'::"regclass");


--
-- TOC entry 2055 (class 2604 OID 16562)
-- Name: shipment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment" ALTER COLUMN "id" SET DEFAULT "nextval"('"shipment_id_seq"'::"regclass");


--
-- TOC entry 2050 (class 2604 OID 16491)
-- Name: traincourier id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourier" ALTER COLUMN "id" SET DEFAULT "nextval"('"traincourier_id_seq"'::"regclass");


--
-- TOC entry 2052 (class 2604 OID 16515)
-- Name: traincourierroute id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourierroute" ALTER COLUMN "id" SET DEFAULT "nextval"('"traincourierroute_id_seq"'::"regclass");


--
-- TOC entry 2048 (class 2604 OID 16428)
-- Name: user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user" ALTER COLUMN "id" SET DEFAULT "nextval"('"user_id_seq"'::"regclass");


--
-- TOC entry 2205 (class 0 OID 16436)
-- Dependencies: 188
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "address" ("id", "address1", "address2", "zipcode", "city") FROM stdin;
1	TestStraat	TestStraat2	TestCode	TestStad
\.


--
-- TOC entry 2233 (class 0 OID 0)
-- Dependencies: 187
-- Name: address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"address_id_seq"', 1, false);


--
-- TOC entry 2213 (class 0 OID 16530)
-- Dependencies: 196
-- Data for Name: externalcourier; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "externalcourier" ("id", "companyname") FROM stdin;
\.


--
-- TOC entry 2234 (class 0 OID 0)
-- Dependencies: 195
-- Name: externalcourier_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"externalcourier_id_seq"', 1, false);


--
-- TOC entry 2215 (class 0 OID 16538)
-- Dependencies: 198
-- Data for Name: package; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "package" ("id", "originaddressid", "destinationaddressid", "weight", "value", "details") FROM stdin;
\.


--
-- TOC entry 2235 (class 0 OID 0)
-- Dependencies: 197
-- Name: package_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"package_id_seq"', 1, false);


--
-- TOC entry 2209 (class 0 OID 16501)
-- Dependencies: 192
-- Data for Name: route; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "route" ("id", "origin", "destination") FROM stdin;
\.


--
-- TOC entry 2236 (class 0 OID 0)
-- Dependencies: 191
-- Name: route_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"route_id_seq"', 1, false);


--
-- TOC entry 2217 (class 0 OID 16559)
-- Dependencies: 200
-- Data for Name: shipment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "shipment" ("id", "originaddressid", "destinationaddressid", "cost", "packageid", "couriertype", "traincourierid", "externalcourierid") FROM stdin;
\.


--
-- TOC entry 2237 (class 0 OID 0)
-- Dependencies: 199
-- Name: shipment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"shipment_id_seq"', 1, false);


--
-- TOC entry 2207 (class 0 OID 16488)
-- Dependencies: 190
-- Data for Name: traincourier; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "traincourier" ("id", "vogapproved", "identification", "userid") FROM stdin;
\.


--
-- TOC entry 2238 (class 0 OID 0)
-- Dependencies: 189
-- Name: traincourier_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"traincourier_id_seq"', 1, false);


--
-- TOC entry 2211 (class 0 OID 16512)
-- Dependencies: 194
-- Data for Name: traincourierroute; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "traincourierroute" ("id", "day", "traincourierid", "routeid") FROM stdin;
\.


--
-- TOC entry 2239 (class 0 OID 0)
-- Dependencies: 193
-- Name: traincourierroute_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"traincourierroute_id_seq"', 1, false);


--
-- TOC entry 2203 (class 0 OID 16425)
-- Dependencies: 186
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY "user" ("id", "firstname", "lastname", "prefix", "password", "email", "isemployee", "addressid") FROM stdin;
19	Ewout	Schrotenboer	test	testpassword	ewout@ewout.io	f	1
\.


--
-- TOC entry 2240 (class 0 OID 0)
-- Dependencies: 185
-- Name: user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('"user_id_seq"', 20, true);


--
-- TOC entry 2057 (class 2606 OID 16990)
-- Name: user Email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT "Email" UNIQUE ("email");


--
-- TOC entry 2241 (class 0 OID 0)
-- Dependencies: 2057
-- Name: CONSTRAINT "Email" ON "user"; Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON CONSTRAINT "Email" ON "user" IS 'logon is through email + password, should be unique';


--
-- TOC entry 2061 (class 2606 OID 16444)
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "address"
    ADD CONSTRAINT "address_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2069 (class 2606 OID 16535)
-- Name: externalcourier externalcourier_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "externalcourier"
    ADD CONSTRAINT "externalcourier_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2071 (class 2606 OID 16546)
-- Name: package package_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "package"
    ADD CONSTRAINT "package_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2065 (class 2606 OID 16509)
-- Name: route route_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "route"
    ADD CONSTRAINT "route_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2073 (class 2606 OID 16567)
-- Name: shipment shipment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "shipment_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2063 (class 2606 OID 16493)
-- Name: traincourier traincourier_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourier"
    ADD CONSTRAINT "traincourier_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2067 (class 2606 OID 16517)
-- Name: traincourierroute traincourierroute_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourierroute"
    ADD CONSTRAINT "traincourierroute_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2059 (class 2606 OID 16433)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT "user_pkey" PRIMARY KEY ("id");


--
-- TOC entry 2074 (class 2606 OID 16481)
-- Name: user address; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT "address" FOREIGN KEY ("addressid") REFERENCES "address"("id");


--
-- TOC entry 2078 (class 2606 OID 16598)
-- Name: package destinationaddres; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "package"
    ADD CONSTRAINT "destinationaddres" FOREIGN KEY ("destinationaddressid") REFERENCES "address"("id");


--
-- TOC entry 2080 (class 2606 OID 16573)
-- Name: shipment destinationaddress; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "destinationaddress" FOREIGN KEY ("destinationaddressid") REFERENCES "address"("id");


--
-- TOC entry 2081 (class 2606 OID 16588)
-- Name: shipment externalcourier; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "externalcourier" FOREIGN KEY ("externalcourierid") REFERENCES "externalcourier"("id");


--
-- TOC entry 2082 (class 2606 OID 16568)
-- Name: shipment originaddress; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "originaddress" FOREIGN KEY ("originaddressid") REFERENCES "address"("id");


--
-- TOC entry 2079 (class 2606 OID 16593)
-- Name: package originaddress; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "package"
    ADD CONSTRAINT "originaddress" FOREIGN KEY ("originaddressid") REFERENCES "address"("id");


--
-- TOC entry 2083 (class 2606 OID 16578)
-- Name: shipment package; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "package" FOREIGN KEY ("packageid") REFERENCES "package"("id");


--
-- TOC entry 2077 (class 2606 OID 16523)
-- Name: traincourierroute route; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourierroute"
    ADD CONSTRAINT "route" FOREIGN KEY ("routeid") REFERENCES "route"("id");


--
-- TOC entry 2076 (class 2606 OID 16518)
-- Name: traincourierroute traincourier; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourierroute"
    ADD CONSTRAINT "traincourier" FOREIGN KEY ("traincourierid") REFERENCES "traincourier"("id");


--
-- TOC entry 2084 (class 2606 OID 16583)
-- Name: shipment traincourier; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "traincourier" FOREIGN KEY ("traincourierid") REFERENCES "traincourier"("id");


--
-- TOC entry 2075 (class 2606 OID 16494)
-- Name: traincourier user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourier"
    ADD CONSTRAINT "user" FOREIGN KEY ("userid") REFERENCES "user"("id");


-- Completed on 2017-06-03 14:30:34

--
-- PostgreSQL database dump complete
--

