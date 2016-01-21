'use strict';

angular.module('ligabasquetApp').controller('JugadorDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Jugador', 'Equipo', 'Estadisticasjugadorpartido',
        function($scope, $stateParams, $modalInstance, entity, Jugador, Equipo, Estadisticasjugadorpartido) {

        $scope.jugador = entity;
        $scope.equipos = Equipo.query();
        $scope.estadisticasjugadorpartidos = Estadisticasjugadorpartido.query();
        $scope.load = function(id) {
            Jugador.get({id : id}, function(result) {
                $scope.jugador = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('ligabasquetApp:jugadorUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jugador.id != null) {
                Jugador.update($scope.jugador, onSaveFinished);
            } else {
                Jugador.save($scope.jugador, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
