# -*- coding: utf-8 -*-
"""
Wrapper für java.sql:
Erstelle einige Sachen im Voraus, um Boilerplate zu minimieren.
"""
from java.lang import Class
from java.sql import DriverManager, SQLException
import sys
from os.path import abspath
sys.path.append(abspath("../lib/sqlite-jdbc-3.40.1.0.jar"))
from org.sqlite import JDBC

# nach https://wiki.python.org/jython/DatabaseExamples

URL = "jdbc:sqlite:data.db"
TESTURL = "jdbc:sqlite:test.db"
DRIVER = "org.sqlite.JDBC"


def getConnection(testing=False):
    try:
        JDBC()
    except Exception, msg:
        print msg
        sys.exit(-1)

    try:
        if testing:
            dbConn = DriverManager.getConnection(TESTURL)
        else:
            dbConn = DriverManager.getConnection(URL)

    except SQLException, msg:
        print msg
        sys.exit(-1)

    return dbConn


if __name__ == "__main__":  # Tests durchführen
    TABLE_NAME = "planet"
    TABLE_DROPPER = "drop table if exists %s;" % TABLE_NAME
    TABLE_CREATOR = "create table %s (name, size, solar_distance);" % TABLE_NAME
    RECORD_INSERTER = "insert into %s values (?, ?, ?);" % TABLE_NAME
    PLANET_QUERY = """
    select name, size, solar_distance
    from %s
    order by size, solar_distance desc
""" % TABLE_NAME

    data = [  # Daten ebenfalls von https://wiki.python.org/jython/DatabaseExamples übernommen.
        ('mercury', 'small', 57),
        ('venus', 'small', 107),
        ('earth', 'small', 150),
        ('mars', 'small', 229),
        ('jupiter', 'large', 777),
        ('saturn', 'large', 888),
        ('uranus', 'medium', 2871),
        ('neptune', 'medium', 4496),
        ('pluto', 'tiny', 5869),
    ]


    def populateTable(dbConn, feedstock):
        """
            Given an open connection to a SQLite database and a list of tuples
            with the data to be inserted, insert the data into the target table.
        """
        try:
            preppedStmt = dbConn.prepareStatement(RECORD_INSERTER)
            for name, size, distance in feedstock:
                preppedStmt.setString(1, name)
                preppedStmt.setString(2, size)
                preppedStmt.setInt(3, distance)
                preppedStmt.addBatch()
            dbConn.setAutoCommit(False)
            preppedStmt.executeBatch()
            dbConn.setAutoCommit(True)
        except SQLException, msg:
            print msg
            return False

        return True


    conn = getConnection(testing=True)

    stmt = conn.createStatement()
    try:
        stmt.executeUpdate(TABLE_DROPPER)
        stmt.executeUpdate(TABLE_CREATOR)
    except SQLException, msg:
        print msg
        sys.exit(1)

    if populateTable(conn, data):
        resultSet = stmt.executeQuery(PLANET_QUERY)
        while resultSet.next():
            name = resultSet.getString("name")
            size = resultSet.getString("size")
            dist = resultSet.getInt("solar_distance")
            print "%-16.16s  %-8.8s  %4d" % (name, size, dist)

    stmt.close()
    conn.close()
