DROP TABLE IF EXISTS task_comments;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(254) NOT NULL UNIQUE,
    display_name VARCHAR(100),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_username_not_blank
        CHECK (LENGTH(TRIM(username)) > 0),

    CONSTRAINT chk_email_format
        CHECK (email LIKE '%_@_%._%')
);

CREATE TABLE categories (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,

    CONSTRAINT chk_category_name_not_blank
        CHECK (LENGTH(TRIM(name)) > 0)
);

CREATE TABLE tasks (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    category_id BIGINT,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    priority INTEGER NOT NULL DEFAULT 3,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    due_date DATE,
    estimated_hours NUMERIC(6, 2),
    completed_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_task_owner
        FOREIGN KEY (owner_id)
            REFERENCES users(id)
            ON DELETE RESTRICT,

    CONSTRAINT fk_task_category
        FOREIGN KEY (category_id)
            REFERENCES categories(id)
            ON DELETE SET NULL,

    CONSTRAINT chk_task_title_not_blank
        CHECK (LENGTH(TRIM(title)) > 0),

    CONSTRAINT chk_task_priority
        CHECK (priority BETWEEN 1 AND 5),

    CONSTRAINT chk_task_status
        CHECK (status IN ('OPEN', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')),

    CONSTRAINT chk_estimated_hours
        CHECK (estimated_hours IS NULL OR estimated_hours > 0),

    CONSTRAINT chk_completed_timestamp
        CHECK (
            (status = 'COMPLETED' AND completed_at IS NOT NULL)
                OR
            (status <> 'COMPLETED' AND completed_at IS NULL)
            )
);

CREATE TABLE task_comments (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    task_id BIGINT NOT NULL,
    comment_text TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_comment_task
        FOREIGN KEY (task_id)
            REFERENCES tasks(id)
            ON DELETE CASCADE,

    CONSTRAINT chk_comment_not_blank
        CHECK (LENGTH(TRIM(comment_text)) > 0)
);

CREATE INDEX idx_tasks_owner_id ON tasks(owner_id);
CREATE INDEX idx_tasks_category_id ON tasks(category_id);
CREATE INDEX idx_tasks_status ON tasks(status);
CREATE INDEX idx_comments_task_id ON task_comments(task_id);