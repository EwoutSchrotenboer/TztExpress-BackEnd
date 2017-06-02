--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.3
-- Dumped by pg_dump version 9.6.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: SCHEMA "public"; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA "public" IS 'standard public schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner:
--

CREATE EXTENSION IF NOT EXISTS "plpgsql" WITH SCHEMA "pg_catalog";


--
-- Name: EXTENSION "plpgsql"; Type: COMMENT; Schema: -; Owner:
--

COMMENT ON EXTENSION "plpgsql" IS 'PL/pgSQL procedural language';


SET search_path = "public", pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
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
-- Name: Address_Id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Address_Id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "Address_Id_seq" OWNER TO "postgres";

--
-- Name: Address_Id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Address_Id_seq" OWNED BY "address"."id";


--
-- Name: externalcourier; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "externalcourier" (
    "id" bigint NOT NULL,
    "companyname" character varying(255) NOT NULL
);


ALTER TABLE "externalcourier" OWNER TO "postgres";

--
-- Name: Externalcourier_Id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Externalcourier_Id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "Externalcourier_Id_seq" OWNER TO "postgres";

--
-- Name: Externalcourier_Id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Externalcourier_Id_seq" OWNED BY "externalcourier"."id";


--
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
-- Name: Package_Id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Package_Id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "Package_Id_seq" OWNER TO "postgres";

--
-- Name: Package_Id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Package_Id_seq" OWNED BY "package"."id";


--
-- Name: route; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "route" (
    "id" bigint NOT NULL,
    "origin" character varying(255) NOT NULL,
    "destination" character varying(255) NOT NULL
);


ALTER TABLE "route" OWNER TO "postgres";

--
-- Name: Route_Id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Route_Id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "Route_Id_seq" OWNER TO "postgres";

--
-- Name: Route_Id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Route_Id_seq" OWNED BY "route"."id";


--
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
-- Name: Shipment_Id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Shipment_Id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "Shipment_Id_seq" OWNER TO "postgres";

--
-- Name: Shipment_Id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Shipment_Id_seq" OWNED BY "shipment"."id";


--
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
-- Name: Traincourier_Id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Traincourier_Id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "Traincourier_Id_seq" OWNER TO "postgres";

--
-- Name: Traincourier_Id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Traincourier_Id_seq" OWNED BY "traincourier"."id";


--
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
-- Name: Traincourierroute_Id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "Traincourierroute_Id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "Traincourierroute_Id_seq" OWNER TO "postgres";

--
-- Name: Traincourierroute_Id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "Traincourierroute_Id_seq" OWNED BY "traincourierroute"."id";


--
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
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "user_id_seq" OWNED BY "user"."id";


--
-- Name: address id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "address" ALTER COLUMN "id" SET DEFAULT "nextval"('"Address_Id_seq"'::"regclass");


--
-- Name: externalcourier id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "externalcourier" ALTER COLUMN "id" SET DEFAULT "nextval"('"Externalcourier_Id_seq"'::"regclass");


--
-- Name: package id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "package" ALTER COLUMN "id" SET DEFAULT "nextval"('"Package_Id_seq"'::"regclass");


--
-- Name: route id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "route" ALTER COLUMN "id" SET DEFAULT "nextval"('"Route_Id_seq"'::"regclass");


--
-- Name: shipment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment" ALTER COLUMN "id" SET DEFAULT "nextval"('"Shipment_Id_seq"'::"regclass");


--
-- Name: traincourier id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourier" ALTER COLUMN "id" SET DEFAULT "nextval"('"Traincourier_Id_seq"'::"regclass");


--
-- Name: traincourierroute id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourierroute" ALTER COLUMN "id" SET DEFAULT "nextval"('"Traincourierroute_Id_seq"'::"regclass");


--
-- Name: user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user" ALTER COLUMN "id" SET DEFAULT "nextval"('"user_id_seq"'::"regclass");


--
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "address"
    ADD CONSTRAINT "address_pkey" PRIMARY KEY ("id");


--
-- Name: externalcourier externalcourier_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "externalcourier"
    ADD CONSTRAINT "externalcourier_pkey" PRIMARY KEY ("id");


--
-- Name: package package_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "package"
    ADD CONSTRAINT "package_pkey" PRIMARY KEY ("id");


--
-- Name: route route_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "route"
    ADD CONSTRAINT "route_pkey" PRIMARY KEY ("id");


--
-- Name: shipment shipment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "shipment_pkey" PRIMARY KEY ("id");


--
-- Name: traincourier traincourier_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourier"
    ADD CONSTRAINT "traincourier_pkey" PRIMARY KEY ("id");


--
-- Name: traincourierroute traincourierroute_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourierroute"
    ADD CONSTRAINT "traincourierroute_pkey" PRIMARY KEY ("id");


--
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT "user_pkey" PRIMARY KEY ("id");


--
-- Name: user address; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT "address" FOREIGN KEY ("addressid") REFERENCES "address"("id");


--
-- Name: package destinationaddres; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "package"
    ADD CONSTRAINT "destinationaddres" FOREIGN KEY ("destinationaddressid") REFERENCES "address"("id");


--
-- Name: shipment destinationaddress; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "destinationaddress" FOREIGN KEY ("destinationaddressid") REFERENCES "address"("id");


--
-- Name: shipment externalcourier; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "externalcourier" FOREIGN KEY ("externalcourierid") REFERENCES "externalcourier"("id");


--
-- Name: shipment originaddress; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "originaddress" FOREIGN KEY ("originaddressid") REFERENCES "address"("id");


--
-- Name: package originaddress; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "package"
    ADD CONSTRAINT "originaddress" FOREIGN KEY ("originaddressid") REFERENCES "address"("id");


--
-- Name: shipment package; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "package" FOREIGN KEY ("packageid") REFERENCES "package"("id");


--
-- Name: traincourierroute route; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourierroute"
    ADD CONSTRAINT "route" FOREIGN KEY ("routeid") REFERENCES "route"("id");


--
-- Name: traincourierroute traincourier; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourierroute"
    ADD CONSTRAINT "traincourier" FOREIGN KEY ("traincourierid") REFERENCES "traincourier"("id");


--
-- Name: shipment traincourier; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "shipment"
    ADD CONSTRAINT "traincourier" FOREIGN KEY ("traincourierid") REFERENCES "traincourier"("id");


--
-- Name: traincourier user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "traincourier"
    ADD CONSTRAINT "user" FOREIGN KEY ("userid") REFERENCES "user"("id");


--
-- PostgreSQL database dump complete
--