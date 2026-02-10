describe("CRUD de Materiais", () => {
  it("Deve cadastrar uma nova matéria-prima", () => {
    cy.request("POST", "http://localhost:8080/materials", {
      name: "Aço Inox",
      description: "Chapa de aço 2mm",
      stockQuantity: 100,
    }).then((response) => {
      expect(response.status).to.eq(201);
      expect(response.body.name).to.eq("Aço Inox");
    });
  });
});
