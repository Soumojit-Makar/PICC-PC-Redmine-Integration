-- ==============================================================================
-- Docker Compose DB Init Script
-- Creates both Redmine databases on first startup.
-- The 'redmine_primary' database is created automatically by POSTGRES_DB env var.
-- This script creates the second database for the support Redmine instance.
-- ==============================================================================

-- Support Redmine database
SELECT 'CREATE DATABASE redmine_support OWNER redmine'
WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'redmine_support'
)\gexec

GRANT ALL PRIVILEGES ON DATABASE redmine_support TO redmine;
