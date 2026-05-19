<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=utf-8");

require_once '../service/GestionnaireContacts.php';

// Récupère et retourne toutes les entrées du carnet de contacts
$gestionnaire = new GestionnaireContacts();
$entrees = $gestionnaire->obtenirTousLesContacts();
echo json_encode($entrees, JSON_UNESCAPED_UNICODE);
