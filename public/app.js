var app = angular.module("fbank", ["ngRoute", "ui.bootstrap", "angular-flash.service", "angular-flash.flash-alert-directive"]);

app.config([
    "$routeProvider",
    function($routeProvider) {
        return $routeProvider.when("/", {
            templateUrl: "pages/welcome.html",
            controller: "WelcomeCtrl"
        }).when("/clients/:clientId/accounts", {
            templateUrl: "pages/accounts.html",
            controller: "AccountsCtrl"
        }).otherwise({
            redirectTo: "/"
        });
    }
]);


app.controller("WelcomeCtrl", ["$scope", "$modal", "serverBridge", "$log", "flash", "$location", function($scope, $modal, $serverBridge, $log, $flash, $location) {
    init();
    $scope.clients;

    function init() {
        $serverBridge.getClients().success(function(response) {
            $scope.clients = response;
        });
    }

    $scope.openRegisterModal = function(size) {
        var modalInstance = $modal.open({
            templateUrl: "pages/tools/registerModal.html",
            controller: "ModalRegClientCtrl",
            size: size
        });

        modalInstance.result.then(function(user) {
            $serverBridge.registerClient(user).success(function(response) {
                $scope.clients.push(user);
                $flash.to('alert-1').success = response.message;
            }).error(function(error) {
                $flash.to('alert-1').error = error.message;
            });
        }, function() {
            $log.info("Modal dismissed at: " + new Date());
        });
    }

    $scope.deleteClient = function(size, client) {
        var modalInstance = $modal.open({
            templateUrl: "pages/tools/confirmationModal.html",
            controller: "ModalConfCtrl",
            size: size
        });
        modalInstance.result.then(function() {
            $serverBridge.deleteClient(client.id).success(function(response) {
                var index = $scope.clients.indexOf(client);
                $scope.clients.splice(index, 1);
                $flash.to('alert-1').success = response.message;
            }).error(function(error) {
                $flash.to('alert-1').error = error.message;
            });
        }, function() {
            $log.info("Modal dismissed at: " + new Date());
        });
    }

    $scope.addAccount = function(size, client) {
        var modalInstance = $modal.open({
            templateUrl: "pages/tools/addAccountModal.html",
            controller: "ModalAddAccountCtrl",
            size: size
        });
        modalInstance.result.then(function(balance) {
            $serverBridge.registerAccountToClient(client.id, balance).success(function(response) {
                $flash.to('alert-1').success = response.message;
            }).error(function(error) {
                $flash.to('alert-1').error = error.message;
            });
        }, function() {
            $log.info("Modal dismissed at: " + new Date());
        });
    }

    $scope.editClient = function(size, client) {
        var modalInstance = $modal.open({
            templateUrl: "pages/tools/editClientModal.html",
            controller: "ModalEditClientCtrl",
            size: size,
            resolve: {
                client: function() {
                    return client;
                }
            }
        });

        modalInstance.result.then(function(newClient) {
            $serverBridge.editClient(client.id, newClient).success(function(response) {
                newClient.id = client.id;
                var index = $scope.clients.indexOf(client);
                $scope.clients[index] = newClient;
                $flash.to('alert-1').success = response.message;
            }).error(function(err) {
                $flash.to('alert-1').error = err.message;
            });
        }, function() {
            $log.info("Modal dismissed at: " + new Date());
        });
    }

    $scope.seeAccounts = function(client) {
        $location.path("/clients/" + client.id + "/accounts");
    }
}]);


app.controller("ModalEditClientCtrl", function($scope, $modalInstance, client) {
    $scope.newClient = {
        name: client.name,
        phone: client.phone,
        address: client.address
    };
    $scope.edit = function() {
        $modalInstance.close($scope.newClient);
    }

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
});


app.controller("ModalAddAccountCtrl", function($scope, $modalInstance) {
    $scope.initBalance;

    $scope.addAccount = function() {
        $modalInstance.close($scope.initBalance);
    }
    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
});


app.controller("ModalRegClientCtrl", function($scope, $modalInstance) {
    $scope.user = {
        name: "",
        phone: "",
        address: ""
    }
    $scope.register = function() {
        $modalInstance.close($scope.user);
    }
    $scope.cancel = function() {
        $modalInstance.dismiss("cancel");
    }
});

app.controller("ModalConfCtrl", function($scope, $modalInstance) {
    $scope.ok = function() {
        $modalInstance.close();
    }

    $scope.cancel = function() {
        $modalInstance.dismiss("cancel");
    }

});



app.controller("AccountsCtrl", ["$scope", "$modal", "serverBridge", "$log", "flash", "$routeParams", "$location", function($scope, $modal, $serverBridge, $log, $flash, $routeParams, $location) {
    init();
    $scope.client;
    $scope.accounts;

    function init() {
        $serverBridge.getClient($routeParams.clientId).success(function(response) {
            $scope.client = response;
            $scope.accounts = $scope.client.accounts;
        }).error(function() {
            $location.path("/")
        });
    }

    $scope.deleteAccount = function(size, account) {
        var modalInstance = $modal.open({
            templateUrl: "pages/tools/confirmationModal.html",
            controller: "ModalConfCtrl",
            size: size
        });
        modalInstance.result.then(function() {
            $serverBridge.deleteAccount(account.number).success(function(response) {
                var index = $scope.accounts.indexOf(account);
                $scope.accounts.splice(index, 1);
                $flash.to('alert-2').success = response.message;
            }).error(function(err) {
                $flash.to('alert-2').error = response.message;
            });
        }, function() {
            $log.info("Modal dismissed at: " + new Date());
        });
    }

    $scope.addAccount = function(size) {
        var modalInstance = $modal.open({
            templateUrl: "pages/tools/addAccountModal.html",
            controller: "ModalAddAccountCtrl",
            size: size
        });
        modalInstance.result.then(function(balance) {
            $serverBridge.registerAccountToClient($scope.client.id, balance).success(function(response) {
                init();
                $flash.to('alert-2').success = response.message;
            }).error(function(error) {
                $flash.to('alert-2').error = error.message;
            });
        }, function() {
            $log.info("Modal dismissed at: " + new Date());
        });
    }

    $scope.registerMovement = function(size, account) {
        var modalInstance = $modal.open({
            templateUrl: "pages/tools/addMovementModal.html",
            controller: "ModalAddMovementCtrl",
            size: size
        });

    }
}]);


app.controller("ModalAddMovementCtrl", function($scope, $modalInstance) {
    $scope.movement = {
    	type: "",
    	value: ""
    };

    $scope.register = function() {
        $modalInstance.close($scope.initBalance);
    }
    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
});


app.service("serverBridge", ["$http", function($http) {
    this.getClient = function(clientId) {
        return $http.get("/clients/" + clientId);
    }

    this.getClients = function() {
        return $http.get("/clients");
    }

    this.registerClient = function(client) {
        return $http.post("/clients", client);
    }

    this.deleteClient = function(id) {
        return $http.delete("/clients/" + id);
    }

    this.registerAccountToClient = function(clientId, initBalance) {
        var req = {
            initBalance: initBalance
        };
        console.log(req);
        return $http.post("/clients/" + clientId + "/accounts", req);
    }

    this.editClient = function(clientId, newClient) {
        return $http.put("/clients/" + clientId, newClient);
    }

    this.deleteAccount = function(accountId) {
        return $http.delete("/accounts/" + accountId);
    }

}]);
