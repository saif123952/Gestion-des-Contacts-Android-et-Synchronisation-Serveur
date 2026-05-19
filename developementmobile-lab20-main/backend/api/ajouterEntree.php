<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=utf-8");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type");

require_once '../service/GestionnaireContacts.php';

// Lecture et décodage du corps JSON de la requête
$donnees = json_decode(file_get_contents("php://input"), true);

// Vérification des champs obligatoires
if (!is_array($donnees) || empty($donnees['nom']) || empty($donnees['telephone'])) {
    http_response_code(400);
    echo json_encode(["succes" => false, "message" => "Champs 'nom' et 'telephone' requis"], JSON_UNESCAPED_UNICODE);
    exit;
}

// Nettoyage du numéro avant validation du format
$telephone = preg_replace('/\s+/', '', $donnees['telephone']);
if (strlen($telephone) < 6 || strlen($telephone) > 20) {
    http_response_code(400);
    echo json_encode(["succes" => false, "message" => "Numéro de téléphone invalide (6 à 20 chiffres)"], JSON_UNESCAPED_UNICODE);
    exit;
}

$gestionnaire = new GestionnaireContacts();
$resultat = $gestionnaire->ajouterContact(
    $donnees['nom'],
    $telephone,
    $donnees['origine'] ?? 'mobile'
);
echo json_encode($resultat, JSON_UNESCAPED_UNICODE);
