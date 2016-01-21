'use strict';

angular.module('ligabasquetApp')
    .controller('EstadisticasJugadorPartidoController', function ($scope, EstadisticasJugadorPartido, ParseLinks) {
        $scope.estadisticasJugadorPartidos = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            EstadisticasJugadorPartido.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.estadisticasJugadorPartidos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            EstadisticasJugadorPartido.get({id: id}, function(result) {
                $scope.estadisticasJugadorPartido = result;
                $('#deleteEstadisticasJugadorPartidoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            EstadisticasJugadorPartido.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEstadisticasJugadorPartidoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.estadisticasJugadorPartido = {canastas: null, asistencias: null, faltas: null, id: null};
        };
    });
