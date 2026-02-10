it("Deve detalhar produtos e materiais no log", () => {
  cy.request("GET", "http://localhost:8080/production/suggest").then(
    (response) => {
      expect(response.status).to.eq(200);

      response.body.sugestoes.forEach((item) => {
        cy.log(`PRODUTO: ${item.produto} | Qtd: ${item.quantidade}`);

        item.materiaisUtilizados.forEach((mat) => {
          cy.log(
            `   -  Material: ${mat.nome} (Gasto Total: ${mat.gastoTotal})`,
          );
        });
      });

      cy.log(` VALOR TOTAL GERAL: R$ ${response.body.valorTotalGeral}`);
    },
  );
});
