package dao;


import java.io.File;

/**
 * 
 */
public class NetworkMemory {


        private static final String OPERATOR = "persist";
        
        
        
        // file system
        private static final String 
                OCAML_REP = "ocaml",
                OCAML_CLI_REP = OCAML_REP + File.separator + "ocaml-cli",
                DATA_REP = OCAML_CLI_REP + File.separator + "data",
                USERS_CSV = DATA_REP + File.separator + "users.csv",
                POSTS_CSV = OCAML_REP + File.separator + "posts.csv";
        
        
        private static final String 
        		FOLLOW = "flw";
   

        
        // CRUD operator 
        private static final String 
                CREATE = "C",
                READ = "R",
                UPDATE = "U",
                DELETE = "D";
        
        
        /**
         * Makes a command 
         * @param crud_args -C, -R, -U, -D
         * @param csvname a path to a csv file  
         * @param args 
         * @return a comand such as : persist -CRUD agrs... -- csvname
         */
        private static String getCmd(String crud_args, String csvname, String... args) {
                String args_str = String.join(" ", args); // joining args for formating 
                String format = "persist -%s %s -- %s";
                return String.format(format, crud_args, args_str, csvname);
        }


        private DataAccessObject dao;


        public NetworkMemory() {
                dao = new DataAccessObject(OCAML_CLI_REP, OPERATOR);
             
        }
        
        
        /**
         * Create a csv representationf an user
         * @param id
         * @return console out
         */
        public String[] createUser(String id) {
                return dao.processCmd(
                        getCmd(CREATE, USERS_CSV, id)
                );
        }
        
        
        public String[] deleteUser(String id) {
            return dao.processCmd(
                    getCmd(DELETE, USERS_CSV, id)
            );
        }
        
        
        public String[][] readUser() {
        	String[] lines = dao.processCmd(getCmd(READ, USERS_CSV));
        	String[][] sheet = new String[lines.length][];
        	for(int i = 0; i < lines.length; i++) sheet[i] = lines[i].split(" ");
        	return sheet;
        }
        
        
        public String[] updateFollow(String followerId, String followedId) {
        	System.out.println("appel");
        	return dao.processCmd(
                    getCmd(UPDATE, USERS_CSV, FOLLOW, followerId, followedId)
            );
        }

}
