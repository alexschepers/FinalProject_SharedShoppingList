package edu.uga.cs.finalproject_sharedshoppinglist;

public class Roommate {

        private String roommateName;
        private double spent;

        public Roommate()
        {
            this.roommateName = null;
            this.spent = -1.0;
        }
        public Roommate( String roommateName, double spent) {
            this.roommateName = roommateName;
            this.spent = spent;

        }
        public String getRoommateName() {
            return roommateName;
        }

        public void setRoommateName(String roommateName) {
            this.roommateName = roommateName;
        }

        public double getSpent() {
            return spent;
        }

        public void setSpent(double spent) {
            this.spent = spent;
        }


        public String toString() {
            return roommateName + " " + spent;
        }
}
