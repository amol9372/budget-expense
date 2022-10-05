-- NOTE: the code below contains the SQL for the object itself
-- as well as for its dependencies or children (if applicable).
--
-- This feature is only a convenience in order to allow you to test
-- the whole object's SQL definition at once.
--
-- When exporting or generating the SQL for the whole database model
-- all objects will be placed at their original positions.


-- -- object: postgres | type: ROLE --
-- -- DROP ROLE IF EXISTS postgres;
-- CREATE ROLE postgres WITH
-- 	SUPERUSER;
-- -- ddl-end --
--
-- object: budget_tracker | type: SCHEMA --
-- DROP SCHEMA IF EXISTS budget_tracker CASCADE;
CREATE SCHEMA budget_tracker;
-- ddl-end --
ALTER SCHEMA budget_tracker OWNER TO postgres;
-- ddl-end --

-- object: budget_tracker.users | type: TABLE --
-- DROP TABLE IF EXISTS budget_tracker.users CASCADE;
CREATE TABLE budget_tracker.users (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	email varchar(100) NOT NULL,
	fname varchar(100),
	lname varchar(100) NOT NULL,
	dob date,
	mobile varchar(50),
	"firebaseId" integer,
	last_login timestamp,
	registered boolean NOT NULL,
	created_on timestamp,
	updated_on timestamp,
	CONSTRAINT email_uq UNIQUE (email),
	CONSTRAINT user_id_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE budget_tracker.users OWNER TO postgres;
-- ddl-end --

-- object: budget_tracker."group" | type: TABLE --
-- DROP TABLE IF EXISTS budget_tracker."group" CASCADE;
CREATE TABLE budget_tracker."group" (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	name varchar NOT NULL,
	members smallint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	budget smallint NOT NULL,
	created_by varchar NOT NULL,
	CONSTRAINT group_id_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE budget_tracker."group" OWNER TO postgres;
-- ddl-end --

-- object: budget_tracker.category | type: TABLE --
-- DROP TABLE IF EXISTS budget_tracker.category CASCADE;
CREATE TABLE budget_tracker.category (
	id smallint NOT NULL GENERATED ALWAYS AS IDENTITY ,
	name varchar NOT NULL,
	created_on timestamp,
	CONSTRAINT catgory_id PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE budget_tracker.category OWNER TO postgres;
-- ddl-end --

-- object: budget_tracker.expenses | type: TABLE --
-- DROP TABLE IF EXISTS budget_tracker.expenses CASCADE;
CREATE TABLE budget_tracker.expenses (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	cost float NOT NULL,
	name varchar NOT NULL,
	category_id smallint NOT NULL,
	created_by varchar NOT NULL,
	paid_by varchar NOT NULL,
	stakeholders json,
	group_id smallint,
	created_on timestamp NOT NULL,
	updated_on timestamp,
	user_defined boolean NOT NULL,
	budget_id integer,
	CONSTRAINT expense_id_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE budget_tracker.expenses OWNER TO postgres;
-- ddl-end --

-- object: budget_tracker.user_group | type: TABLE --
-- DROP TABLE IF EXISTS budget_tracker.user_group CASCADE;
CREATE TABLE budget_tracker.user_group (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	user_id smallint,
	group_id smallint,
	CONSTRAINT user_group_id_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE budget_tracker.user_group OWNER TO postgres;
-- ddl-end --

-- object: created_by_fk | type: CONSTRAINT --
-- ALTER TABLE budget_tracker."group" DROP CONSTRAINT IF EXISTS created_by_fk CASCADE;
ALTER TABLE budget_tracker."group" ADD CONSTRAINT created_by_fk FOREIGN KEY (created_by)
REFERENCES budget_tracker.users (email) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: group_id | type: CONSTRAINT --
-- ALTER TABLE budget_tracker.expenses DROP CONSTRAINT IF EXISTS group_id CASCADE;
ALTER TABLE budget_tracker.expenses ADD CONSTRAINT group_id FOREIGN KEY (group_id)
REFERENCES budget_tracker."group" (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: user_id_fk | type: CONSTRAINT --
-- ALTER TABLE budget_tracker.user_group DROP CONSTRAINT IF EXISTS user_id_fk CASCADE;
ALTER TABLE budget_tracker.user_group ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id)
REFERENCES budget_tracker.users (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: group_id_fk | type: CONSTRAINT --
-- ALTER TABLE budget_tracker.user_group DROP CONSTRAINT IF EXISTS group_id_fk CASCADE;
ALTER TABLE budget_tracker.user_group ADD CONSTRAINT group_id_fk FOREIGN KEY (group_id)
REFERENCES budget_tracker."group" (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: budget_tracker.budget | type: TABLE --
-- DROP TABLE IF EXISTS budget_tracker.budget CASCADE;
CREATE TABLE budget_tracker.budget (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	name varchar NOT NULL,
	currency float NOT NULL DEFAULT CAD,
	money_assigned float NOT NULL,
	created_on timestamp NOT NULL,
	updated_on timestamp,
	user_id integer NOT NULL,
	CONSTRAINT budget_id_pk PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE budget_tracker.budget OWNER TO postgres;
-- ddl-end --

-- object: user_id_fk | type: CONSTRAINT --
-- ALTER TABLE budget_tracker.budget DROP CONSTRAINT IF EXISTS user_id_fk CASCADE;
ALTER TABLE budget_tracker.budget ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id)
REFERENCES budget_tracker.users (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: budget_tracker.user_categories | type: TABLE --
-- DROP TABLE IF EXISTS budget_tracker.user_categories CASCADE;
CREATE TABLE budget_tracker.user_categories (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	name varchar NOT NULL,
	created_on timestamp NOT NULL,
	updated_on timestamp,
	CONSTRAINT user_category_id PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE budget_tracker.user_categories OWNER TO postgres;
-- ddl-end --

-- object: budget_tracker.category_budget | type: TABLE --
-- DROP TABLE IF EXISTS budget_tracker.category_budget CASCADE;
CREATE TABLE budget_tracker.category_budget (
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY ,
	user_defined boolean NOT NULL,
	category_id integer NOT NULL,
	allocated float NOT NULL DEFAULT 0.0,
	used float NOT NULL DEFAULT 0.0,
	created_on timestamp NOT NULL,
	updated_on timestamp,
	budget_id integer NOT NULL,
	CONSTRAINT category_budget_id PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE budget_tracker.category_budget OWNER TO postgres;
-- ddl-end --

-- object: budget_fk | type: CONSTRAINT --
-- ALTER TABLE budget_tracker.category_budget DROP CONSTRAINT IF EXISTS budget_fk CASCADE;
ALTER TABLE budget_tracker.category_budget ADD CONSTRAINT budget_fk FOREIGN KEY (budget_id)
REFERENCES budget_tracker.budget (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --

-- object: budget_id_fk | type: CONSTRAINT --
-- ALTER TABLE budget_tracker.expenses DROP CONSTRAINT IF EXISTS budget_id_fk CASCADE;
ALTER TABLE budget_tracker.expenses ADD CONSTRAINT budget_id_fk FOREIGN KEY (budget_id)
REFERENCES budget_tracker.budget (id) MATCH SIMPLE
ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ddl-end --