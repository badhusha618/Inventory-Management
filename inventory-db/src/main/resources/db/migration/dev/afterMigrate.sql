USE `INVENTORY`;

SET @INSERT_SA_QUERY := "INSERT INTO ADMIN_USERS (FULL_NAME, EMAIL, MOBILE, USERNAME, PASSWORD, ROLE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, ACTIVE, DELETED) VALUES ('makinus', 'services@makinus.com', '8939862268', 'makinus', '$2a$10$QImK4xVV4t6zYrcNLY7Pmep1i4NPdH6td.yUEMhVG3UpjmJQKmZ6u', 'S', 'AUTO_CREATOR', NOW(), 'AUTO_CREATOR', NOW(), 'T', 'F')";
SET @EXIST := (SELECT COUNT(*) FROM ADMIN_USERS WHERE ROLE = 'S' AND DELETED = 'F');
SET @SQLSTMT := IF(@EXIST > 0, 'SELECT "INFO: SUPER ADMIN ALREADY AVAILABLE" FROM DUAL', @INSERT_SA_QUERY);
PREPARE STMT FROM @SQLSTMT;
EXECUTE STMT;

SET @INSERT_SA_QUERY_A := "INSERT INTO ADMIN_USERS (FULL_NAME, EMAIL, MOBILE, USERNAME, PASSWORD, ROLE, CREATED_BY, CREATED_DATE, UPDATED_BY, UPDATED_DATE, ACTIVE, DELETED) VALUES ('Demo User', 'support@INVENTORY.in', '8939862268', 'demouser', '$2a$10$tfBGdBHm0eHOggLcFcLn8.SDe0mYRnvqUwxVhdEOqi.Afe7YXJr2m', 'A', 'AUTO_CREATOR', NOW(), 'AUTO_CREATOR', NOW(), 'T', 'F')";
SET @EXIST_A := (SELECT COUNT(*) FROM ADMIN_USERS WHERE ROLE = 'A' AND DELETED = 'F');
SET @SQLSTMT_A := IF(@EXIST_A > 0, 'SELECT "INFO: SUPER ADMIN ALREADY AVAILABLE" FROM DUAL', @INSERT_SA_QUERY_A);
PREPARE STMT_A FROM @SQLSTMT_A;
EXECUTE STMT_A;