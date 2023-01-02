(* les chemins utilisés font l'hypothèse que le projet est organisé   *)
(* sous forme de projet Java en Eclipse (avec repertoire src et bin   *)
(* auquel est rajouté un répertoire ocaml au meme niveau que src.     *)
(* Le repertoire ocaml contient lui-même un sous-repertoire ocaml-cli *)
(* qui contient lui-même un repertoire src pour les sources .ml et le *)
(* répertoire data contenant les fichiers csv.                        *)
(* Les fichiers doivent être créés (vides au minimum).                *)


(* Concevez avec beaucoup d'attention la structure de vos données     *)


let data_directory = "ocaml/ocaml-cli/data"
let users_csv = "users.csv"
let posts_csv = "posts.csv"


let csv_path csvname = data_directory^"/"^csvname
let users_csv_path = csv_path users_csv
let posts_csv_path = csv_path posts_csv


let modify_csv csvname modif =
	let loaded_data = Libcsv.load_csv csvname in
	let modified_data = modif loaded_data in 
	Libcsv.save_csv csvname modified_data


let rec removeIf lst pred =
	match lst with 
	| [] -> []
	| h::t ->
		let nt = removeIf t pred in
		if pred h then nt else h::nt


let removeIfEq lst v = removeIf lst (fun x -> x=v);;



let create_user name =
	modify_csv users_csv_path (fun data -> data@[[name]])


let add_post author text = ()


let read_str str = print_string str;;


let rec read_line lst =
	match lst with 
	| str::tail -> 
		let sep = if tail = [] then "" else " " in 
		read_str (str^sep); read_line tail
	| [] -> ()


let rec read_sheet sheet =
	match sheet with
	| line::tail -> 
		let sep = if tail = [] then "" else "\n" in 
		read_line line; read_str sep; read_sheet tail 
	| [] -> ()


(* --------- CREATE ----------------*)
let create_fn arg_list csvname =
	if      csvname = users_csv_path then
		match arg_list with
		| [name] -> create_user name
	else if csvname = posts_csv_path then 
		match arg_list with
		| [author; text] -> add_post author text


(* --------- READ ----------------*)
let read_fn arg_list csvname =
	read_sheet (Libcsv.load_csv csvname)
		
(* --------- UPDATE ----------------*)	
let update_fn arg_list csvname =
	if      csvname = users_csv_path then
		match arg_list with
		| ["flw"; follower; followed] -> 
			let rec modif data = 
				match data with 
				| (name::lst)::other_lines -> 
					if name = follower 
						then (name::followed::lst)::other_lines (*condition d'arret*)
						else (name::lst)::(modif other_lines)
				| [] -> []	
					in modify_csv csvname modif
		else ()

(**
(name::lst)::other_lines -> 
					if name = follower 
						then (name::follower::lst)::other_lines
						else (name::lst)::(modif other_lines)
				| [] -> []		
*)

(* --------- DELETE ----------------*)
let delete_fn arg_list csvname =
	if      csvname = users_csv_path then
		match arg_list with
		| [name_to_delete] -> 
			let rec modif data = 
				match data with 
				| (name::lst)::other_lines ->
					if name = name_to_delete 
						then modif other_lines
						else (name::(removeIfEq lst name_to_delete))::(modif other_lines)
				| [] -> []	
					in modify_csv csvname modif
		else ()




(* Exemples de tests unitaires de la commande de persistance sur *)
(* un domaine gérant des étudiants, epreuves, modules...         *)
(*
./persist -C 1 Migeon Frederic False -- etudiants.csv
./persist -C 1 CC1 1 KINXIL11 -- epreuves.csv
./persist -C 1 ILU1 6 -- modules.csv

./persist -R -all -- etudiants.csv
./persist -R -first Nom Migeon -- etudiants.csv
./persist -R -last Nom Migeon -- etudiants.csv

./persist -U -all Nom Migeon Migeo -- etudiants.csv
./persist -U -first Nom Migeo Migeon -- etudiants.csv
./persist -U -last Nom Migeo Migeon -- etudiants.csv

./persist -D -all Nom Migeon -- etudiants.csv
./persist -D -first Nom Migeon -- etudiants.csv
./persist -D -last Nom Migeon -- etudiants.csv

*)
