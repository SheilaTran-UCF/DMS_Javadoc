DROP TABLE IF EXISTS employees;

CREATE TABLE employees (
                           id INTEGER PRIMARY KEY AUTOINCREMENT,
                           name TEXT NOT NULL,
                           position TEXT NOT NULL,
                           salary REAL NOT NULL CHECK (salary >= 0),
                           hire_date TEXT NOT NULL,
                           department TEXT NOT NULL,
                           active BOOLEAN NOT NULL
);
INSERT INTO employees (name, position, salary, hire_date, department, active) VALUES
                                                                                  ('Alice Johnson', 'Manager', 75000, '2015-04-23', 'HR', 1),
                                                                                  ('Bob Smith', 'Developer', 85000, '2018-01-12', 'IT', 1),
                                                                                  ('Carol White', 'Sales Rep', 60000, '2019-09-18', 'Sales', 1),
                                                                                  ('David Kim', 'Developer', 88000, '2016-11-03', 'IT', 1),
                                                                                  ('Eva Adams', 'Marketing Coordinator', 55000, '2021-06-01', 'Marketing', 1),
                                                                                  ('Frank Lee', 'Sales Manager', 72000, '2014-03-14', 'Sales', 1),
                                                                                  ('Grace Wu', 'Financial Analyst', 67000, '2017-07-20', 'Finance', 1),
                                                                                  ('Henry Moore', 'HR Assistant', 48000, '2020-02-25', 'HR', 1),
                                                                                  ('Ivy Brown', 'IT Support', 50000, '2022-01-30', 'IT', 1),
                                                                                  ('Jack Green', 'Finance Manager', 95000, '2013-10-10', 'Finance', 0),
                                                                                  ('Karen Thomas', 'Designer', 62000, '2019-05-05', 'Marketing', 1),
                                                                                  ('Leo Wilson', 'Sales Rep', 61000, '2020-08-08', 'Sales', 0),
                                                                                  ('Mia Young', 'Developer', 86000, '2016-12-01', 'IT', 1),
                                                                                  ('Noah Hall', 'HR Coordinator', 53000, '2018-03-19', 'HR', 1),
                                                                                  ('Olivia Scott', 'Marketing Manager', 73000, '2015-07-07', 'Marketing', 1),
                                                                                  ('Paul King', 'Accountant', 68000, '2017-10-17', 'Finance', 1),
                                                                                  ('Quinn Reed', 'Sales Support', 49000, '2021-11-11', 'Sales', 1),
                                                                                  ('Rachel Gray', 'HR Manager', 71000, '2012-06-06', 'HR', 1),
                                                                                  ('Steve Price', 'SysAdmin', 87000, '2014-02-22', 'IT', 1),
                                                                                  ('Tina Bell', 'Writer', 51000, '2023-03-03', 'Marketing', 1);
