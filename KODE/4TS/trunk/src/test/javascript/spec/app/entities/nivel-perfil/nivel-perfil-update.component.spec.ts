/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { NivelPerfilUpdateComponent } from 'app/entities/nivel-perfil/nivel-perfil-update.component';
import { NivelPerfilService } from 'app/entities/nivel-perfil/nivel-perfil.service';
import { NivelPerfil } from 'app/shared/model/nivel-perfil.model';

describe('Component Tests', () => {
  describe('NivelPerfil Management Update Component', () => {
    let comp: NivelPerfilUpdateComponent;
    let fixture: ComponentFixture<NivelPerfilUpdateComponent>;
    let service: NivelPerfilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [NivelPerfilUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NivelPerfilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NivelPerfilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NivelPerfilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NivelPerfil(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new NivelPerfil();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
