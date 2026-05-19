<?php
/**
 * Gère la connexion PDO à la base de données MySQL.
 * En cas d'échec, retourne une réponse JSON d'erreur et arrête l'exécution.
 */
class ConnexionBD {
    private string $hote       = "localhost";
    private string $nomBase    = "carnet_contacts";
    private string $utilisateur = "root";
    private string $motDePasse  = "";

    public function etablirConnexion(): PDO {
        try {
            $dsn = "mysql:host={$this->hote};dbname={$this->nomBase};charset=utf8mb4";
            $pdo = new PDO($dsn, $this->utilisateur, $this->motDePasse);
            $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
            $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
            return $pdo;
        } catch (PDOException $e) {
            http_response_code(500);
            echo json_encode(["succes" => false, "message" => "Connexion impossible à la base de données"]);
            exit;
        }
    }
}
