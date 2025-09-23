import React from "react";
import { Outlet } from "react-router-dom";
import HeaderComp from "./HeaderComp";
import FooterComp from "./FooterComp";

const MainPage = () => {
  return (
    <div className="d-flex flex-column min-vh-100">
      <HeaderComp />

      <main className="flex-grow-1 container mt-4">
        <Outlet />
      </main>

      <FooterComp />
    </div>
  );
};

export default MainPage;
