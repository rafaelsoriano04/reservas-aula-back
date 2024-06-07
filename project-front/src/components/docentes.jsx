import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button } from 'react-bootstrap';
import "../styles/docentes.css";

function Docentes() {
  const [selectedRow, setSelectedRow] = useState(null);
  const [showContextMenu, setShowContextMenu] = useState(false);
  const [contextMenuPosition, setContextMenuPosition] = useState({
    top: 0,
    left: 0,
  });

  const handleRowClick = (e, rowIndex) => {
    setSelectedRow(rowIndex);
    setContextMenuPosition({ top: e.pageY, left: e.pageX });
    setShowContextMenu(true);
  };

  const handleDocumentClick = (e) => {
    if (!e.target.closest(".context-menu") && !e.target.closest("td")) {
      setSelectedRow(null);
      setShowContextMenu(false);
    }
  };

  useEffect(() => {
    document.addEventListener("click", handleDocumentClick);
    return () => {
      document.removeEventListener("click", handleDocumentClick);
    };
  }, []);

  return (
    <div className="container-fluid">
      <div className="content">
        <div className="header">
          <h2>Docentes</h2>
        </div>
        <div className="mt-4">
          <Form id="form-reservas">
            <div className="row">
              <div className="col-md-4">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="cedula">Cédula:</Form.Label>
                  <Form.Control type="text" id="cedula" className="form-control" name="cedula" />
                </Form.Group>
              </div>
              <div className="col-md-4">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="nombre">Nombre:</Form.Label>
                  <Form.Control type="text" id="nombre" className="form-control" name="nombre" />
                </Form.Group>
              </div>
              <div className="col-md-4">
                <Form.Group className="form-group">
                  <Form.Label htmlFor="apellido">Apellido:</Form.Label>
                  <Form.Control type="text" id="apellido" className="form-control" name="apellido" />
                </Form.Group>
              </div>
            </div>
            <div className="button-group mt-4 text-center">
              <Button type="button" className="btn btn-custom">Crear</Button>
              <Button type="button" className="btn btn-custom" id="guardar-btn" style={{ display: 'none' }}>
                Guardar
              </Button>
            </div>
          </Form>
          <table className="table table-bordered mt-4">
            <thead>
              <tr>
                <th>id</th>
                <th>Cédula</th>
                <th>Nombre</th>
                <th>Apellido</th>
              </tr>
            </thead>
            <tbody>
              <tr onClick={(e) => handleRowClick(e, 1)} className={isSelectedRow(1) ? 'selected' : ''}>
                <td>1</td>
                <td>123456789</td>
                <td>John</td>
                <td>Doe</td>
              </tr>
              <tr onClick={(e) => handleRowClick(e, 2)} className={isSelectedRow(2) ? 'selected' : ''}>
                <td>2</td>
                <td>987654321</td>
                <td>Jane</td>
                <td>Doe</td>
              </tr>
            </tbody>
          </table>
          <div
            className="context-menu"
            style={{
              display: showContextMenu && selectedRow !== null ? "block" : "none",
              top: contextMenuPosition.top,
              left: contextMenuPosition.left,
            }}
          >
            <button type="button">Editar</button>
            <button type="button">Eliminar</button>
          </div>
        </div>
      </div>
    </div>
  );

  function isSelectedRow(rowIndex) {
    return selectedRow === rowIndex;
  }
}

export default Docentes;
