db.createUser(
        {
            user: "link-user",
            pwd: "pass",
            roles: [
                {
                    role: "readWrite",
                    db: "Link"
                }
            ]
        }
);
db.grantRolesToUser("link-user", [{ role: "dbOwner", db: "Link" }]);
