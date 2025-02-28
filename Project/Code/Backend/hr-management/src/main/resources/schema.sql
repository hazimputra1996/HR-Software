-- Create Users Table First (since many tables reference it)
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255),
    salary DOUBLE PRECISION,
    daily_working_hours DOUBLE PRECISION,
    number_of_working_days_per_week INTEGER,
    epf_number VARCHAR(255),
    socso_number VARCHAR(255),
    income_tax_number VARCHAR(255),
    registration_number VARCHAR(255),
    employee_number VARCHAR(255),
    hire_date DATE,
    leave_balance INTEGER DEFAULT 0,
    supervisor_id BIGINT REFERENCES users(id)
);

-- Create Company Table (No Dependencies)
CREATE TABLE IF NOT EXISTS company (
    id SERIAL PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    company_registration_number VARCHAR(255) NOT NULL,
    company_number VARCHAR(255),
    company_address TEXT,
    company_city VARCHAR(255),
    company_state VARCHAR(255),
    company_postal_code VARCHAR(20),
    company_country VARCHAR(255),
    company_phone_number VARCHAR(20),
    company_email VARCHAR(255),
    company_website VARCHAR(255),
    company_fax_number VARCHAR(20),
    status VARCHAR(50),
    cut_off_date INTEGER
);

-- Create Leave Types (Independent)
CREATE TABLE IF NOT EXISTS leave_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    min_service_months BIGINT,
    max_service_months BIGINT,
    max_days INTEGER,
    expiry_date VARCHAR(255)
);

-- Create Public Holidays Table (No Dependencies)
CREATE TABLE IF NOT EXISTS public_holidays (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date DATE,
    country VARCHAR(255),
    state VARCHAR(255),
    recurring BOOLEAN DEFAULT FALSE
);

-- Create Deductions Table (Independent)
CREATE TABLE IF NOT EXISTS deductions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL, -- EPF, SOCSO, etc.
    deduction_type VARCHAR(50) NOT NULL, -- Percentage or Fixed
    rate DOUBLE PRECISION,
    max_limit DOUBLE PRECISION,
    description TEXT,
    is_employee_deduction BOOLEAN
);

-- Create Allowance Table (Depends on Users)
CREATE TABLE IF NOT EXISTS AllowanceDO (  
    id SERIAL PRIMARY KEY,  
    user_id BIGINT NOT NULL REFERENCES users(id),  
    name VARCHAR(255),  
    one_time_bonus BOOLEAN,  
    amount DOUBLE PRECISION,  
    allowance_type VARCHAR(50),  
    date_started DATE,  
    date_ended DATE,  
    remarks VARCHAR(255),  
    processed_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP  
);

-- Create Audit Trail Table (Depends on Users)
CREATE TABLE IF NOT EXISTS audit_trail (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    action_by BIGINT REFERENCES users(id),
    action VARCHAR(255),
    module VARCHAR(255),
    old_value TEXT,
    new_value TEXT,
    action_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    remarks TEXT
);

-- Create Employee Deductions Table (Depends on Users and Deductions)
CREATE TABLE IF NOT EXISTS employee_deductions (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    deduction_id BIGINT REFERENCES deductions(id),
    started_date DATE,
    ended_date DATE,
    amount DOUBLE PRECISION
);

-- Create Leave Carry Forward Table (Depends on Users)
CREATE TABLE IF NOT EXISTS leave_carry_forward (  
    id SERIAL PRIMARY KEY,  
    user_id BIGINT REFERENCES users(id),  
    previous_year INTEGER,  
    carried_days INTEGER,  
    expiry_date DATE,  
    processed_on DATE DEFAULT CURRENT_DATE  
);

-- Create Leaves Table (Depends on Users and Leave Types)
CREATE TABLE IF NOT EXISTS leaves (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    leave_type_id BIGINT REFERENCES leave_types(id),
    start_date DATE,
    end_date DATE,
    status VARCHAR(50) DEFAULT 'PENDING',
    applied_on DATE DEFAULT CURRENT_DATE,
    approved_by BIGINT REFERENCES users(id),
    auto_approved BOOLEAN DEFAULT FALSE
);

-- Create Overtime Table (Depends on Users)
CREATE TABLE IF NOT EXISTS overtime (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    overtime_date DATE,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    hours_worked DOUBLE PRECISION,
    overtime_rate DOUBLE PRECISION,
    total_overtime_price DOUBLE PRECISION,
    remarks TEXT
);

-- Create Salary Statements Table (Depends on Users)
CREATE TABLE IF NOT EXISTS salary_statements (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    statement_date DATE,
    start_date DATE,
    end_date DATE,
    base_salary DOUBLE PRECISION,
    remarks TEXT
);

-- Create Salary Statement Allowances Table (Depends on Users, Salary Statements, Allowance)
CREATE TABLE IF NOT EXISTS salary_statements_allowances (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    salary_statement_id BIGINT REFERENCES salary_statements(id),
    allowance_id BIGINT REFERENCES AllowanceDO(id),
    name VARCHAR(255),
    amount DOUBLE PRECISION
);

-- Create Salary Statement Employee Deductions Table (Depends on Users, Salary Statements, Deductions)
CREATE TABLE IF NOT EXISTS salary_statements_employee_deductions (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    salary_statement_id BIGINT REFERENCES salary_statements(id),
    deduction_id BIGINT REFERENCES deductions(id),
    name VARCHAR(255),
    amount DOUBLE PRECISION
);

-- Create Salary Statement Employer Deductions Table (Depends on Users, Salary Statements, Deductions)
CREATE TABLE IF NOT EXISTS salary_statements_employer_deductions (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    salary_statement_id BIGINT REFERENCES salary_statements(id),
    deduction_id BIGINT REFERENCES deductions(id),
    name VARCHAR(255),
    amount DOUBLE PRECISION
);

-- Create Salary Statement Overtime Table (Depends on Users, Salary Statements, Overtime)
CREATE TABLE IF NOT EXISTS salary_statements_overtime (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    overtime_id BIGINT REFERENCES overtime(id),
    salary_statement_id BIGINT REFERENCES salary_statements(id),
    amount DOUBLE PRECISION,
    remarks TEXT
);

-- Create User Deductions Table (Depends on Users, Deductions)
CREATE TABLE IF NOT EXISTS user_deductions (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    deduction_id BIGINT REFERENCES deductions(id),
    started_date DATE,
    ended_date DATE,
    amount DOUBLE PRECISION
);
