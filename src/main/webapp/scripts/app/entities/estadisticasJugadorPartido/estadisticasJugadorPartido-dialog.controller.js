'use strict';

angular.module('ligabasquetApp').controller('EstadisticasJugadorPartidoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'EstadisticasJugadorPartido', 'Jugador', 'Partido',
        function($scope, $stateParams, $modalInstance, entity, EstadisticasJugadorPartido, Jugador, Partido) {

        $scope.estadisticasJugadorPartido = entity;
        $scope.jugadors = Jugador.query();
        $scope.partidos = Partido.query();
        $scope.load = function(id) {
            EstadisticasJugadorPartido.get({id : id}, function(result) {
                $scope.estadisticasJugadorPartido = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('ligabasquetApp:estadisticasJugadorPartidoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.estadisticasJugadorPartido.id != null) {
                EstadisticasJugadorPartido.update($scope.estadisticasJugadorPartido, onSaveFinished);
            } else {
                EstadisticasJugadorPartido.save($scope.estadisticasJugadorPartido, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
