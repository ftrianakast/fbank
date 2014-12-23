var app = angular.module("fbank", ["ngRoute", "ui.bootstrap", "angular-flash.service", "angular-flash.flash-alert-directive", "fiestah.money"]);

app.config([
    "$routeProvider",
    function($routeProvider) {
        return $routeProvider.when("/", {
            templateUrl: "pages/welcome.html",
            controller: "WelcomeCtrl"
        }).when("/clients/:clientId/accounts", {
            templateUrl: "pages/accounts.html",
            controller: "AccountsCtrl"
        }).when("/accounts/:accountId/movements", {
            templateUrl: "pages/movements.html",
            controller: "MovementsCtrl"
        }).when("/clients/:clientId/reports", {
            templateUrl: "pages/report.html",
            controller: "ReportCtrl"
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
                init();
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

    $scope.generateReport = function(size, client) {
        var modalInstance = $modal.open({
            templateUrl: "pages/tools/generateReportModal.html",
            controller: "ModalGenerateReportCtrl",
            size: size
        });

        modalInstance.result.then(function(reportRequest) {
            var path = "/clients/" + client.id + "/reports";
            $location.path(path).search("initDate", reportRequest.initDate).search("endDate", reportRequest.endDate);
        });

    }

    $scope.seeAccounts = function(client) {
        $location.path("/clients/" + client.id + "/accounts");
    }
}]);

app.controller("ModalGenerateReportCtrl", function($scope, $modalInstance) {
    $scope.startDate;
    $scope.endDate;
    $scope.startHour = new Date();
    $scope.endHour = new Date();

    $scope.reportRequest = {
        initDate: "",
        endDate: ""
    }

    $scope.format = "dd-MM-yyyy";
    $scope.hstep = 1;
    $scope.mstep = 1;
    $scope.ismeridian = true;

    $scope.openInitDate = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.openedInitDate = true;
    };


    $scope.openEndDate = function($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.openedEndDate = true;
    };

    $scope.generate = function() {
        $scope.startDate.setHours(($scope.startHour.getHours()));
        $scope.startDate.setMinutes(($scope.startHour.getMinutes()));
        $scope.endDate.setHours(($scope.endHour.getHours()));
        $scope.endDate.setMinutes(($scope.endHour.getMinutes()));
        $scope.reportRequest.initDate = $scope.startDate;
        $scope.reportRequest.endDate = $scope.endDate;
        $modalInstance.close($scope.reportRequest);
    }

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }

});


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
                $flash.to('alert-3').error = response.message;
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

        modalInstance.result.then(function(movement) {
            $serverBridge.registerMovement(account.number, movement).success(function(response) {
                init();
                $flash.to('alert-2').success = response.message;
            }).error(function(error) {
                $flash.error = error.message;
            });
        }, function() {
            $log.info("Modal dismissed at: " + new Date());
        });
    }

    $scope.seeMovements = function(account) {
        $location.path("/accounts/" + account.number + "/movements");
    }
}]);


app.controller("ModalAddMovementCtrl", function($scope, $modalInstance) {
    $scope.movement = {
        type: "",
        value: ""
    };

    $scope.register = function() {
        $modalInstance.close($scope.movement);
    }
    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    }
});

app.controller("MovementsCtrl", ["$scope", "$modal", "serverBridge", "$log", "flash", "$routeParams", "$location", function($scope, $modal, $serverBridge, $log, $flash, $routeParams, $location) {
    init();
    $scope.movements;

    function init() {
        $serverBridge.getMovements($routeParams.accountId).success(function(response) {
            $scope.movements = response;
        }).error(function(error) {
            $flast.to("alert-4").error = error;
        });
    }

    $scope.goBack = function() {
        window.history.back();
    }
}]);


app.controller("ReportCtrl", ["$scope", "$modal", "serverBridge", "$log", "flash", "$routeParams", "$location", function($scope, $modal, $serverBridge, $log, $flash, $routeParams, $location) {
    $scope.report;
    $scope.reportRequest = {};
    init();

    function init() {
        var initDate = new Date($location.search().initDate);
        var endDate = new Date($location.search().endDate);
        $scope.reportRequest.initDate = formatDate(initDate);
        $scope.reportRequest.endDate = formatDate(endDate);

        $serverBridge.generateReport($routeParams.clientId, $scope.reportRequest).success(function(report) {
            $scope.report = report;
        }, function(err) {
            console.log(err);
        });

    }

    $scope.goBack = function() {
        window.history.back();
    }

    function formatDate(date) {
        return n(date.getDate()) + "-" +
            n(date.getMonth() +1) + "-" +
            date.getFullYear() + " " +
            n(date.getHours()) + ":" +
            n(date.getMinutes()) + ":" +
            n(date.getUTCSeconds());
    }

    function n(n) {
        return n > 9 ? "" + n : "0" + n;
    }


}]);

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

    this.registerMovement = function(accountId, movement) {
        return $http.post("/accounts/" + accountId + "/movements", movement);
    }

    this.getMovements = function(accountId) {
        return $http.get("/accounts/" + accountId + "/movements");
    }

    this.generateReport = function(clientId, reportRequest) {
        return $http.post("/clients/" + clientId + "/reports", reportRequest);
    }

}]);
