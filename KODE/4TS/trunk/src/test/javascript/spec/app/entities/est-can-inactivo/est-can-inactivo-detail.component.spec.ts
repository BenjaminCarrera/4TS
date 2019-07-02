/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstCanInactivoDetailComponent } from 'app/entities/est-can-inactivo/est-can-inactivo-detail.component';
import { EstCanInactivo } from 'app/shared/model/est-can-inactivo.model';

describe('Component Tests', () => {
  describe('EstCanInactivo Management Detail Component', () => {
    let comp: EstCanInactivoDetailComponent;
    let fixture: ComponentFixture<EstCanInactivoDetailComponent>;
    const route = ({ data: of({ estCanInactivo: new EstCanInactivo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstCanInactivoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstCanInactivoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstCanInactivoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estCanInactivo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
