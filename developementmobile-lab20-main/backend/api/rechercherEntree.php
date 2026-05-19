<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=utf-8");

require_once '../service/GestionnaireContacts.php';

// Paramètre de recherche — refus si trop court pour éviter les requêtes inutiles
$motCle = isset($_GET['q']) ? trim($_GET['q']) : '';

if (strlen($motCle) < 2) {
    echo json_encode([], JSON_UNESCAPED_UNICODE);
    exit;
}

$gestionnaire = new GestionnaireContacts();
$resultats = $gestionnaire->rechercherParMotCle($motCle);
echo json_encode($resultats, JSON_UNESCAPED_UNICODE);
