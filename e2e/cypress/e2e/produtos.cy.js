it("Deve cadastrar um produto com sua composição (receita)", () => {
  cy.request("POST", "http://localhost:8080/products", {
    name: "Mesa Industrial",
    description: "Mesa de ferro e madeira",
    value: 450.0,
    composition: [
      {
        material: { id: 1 },
        quantityNeeded: 2,
      },
    ],
  }).then((response) => {
    expect(response.status).to.eq(201);
    expect(response.body.composition).to.have.length(1);
  });
});
